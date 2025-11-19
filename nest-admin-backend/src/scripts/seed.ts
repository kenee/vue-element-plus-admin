import { NestFactory } from '@nestjs/core';
import { AppModule } from '../app.module';
import { UserService } from '../user/user.service';
import { RoleService } from '../role/role.service';
import { CreateUserDto } from '../user/dto/create-user.dto';
import { CreateRoleDto } from '../role/dto/create-role.dto';

async function bootstrap() {
    const app = await NestFactory.createApplicationContext(AppModule);
    const userService = app.get(UserService);
    const roleService = app.get(RoleService);

    // 1. Seed Roles
    const rolesData: CreateRoleDto[] = [
        {
            roleName: 'Super Admin',
            roleValue: 'admin',
            status: 1,
            remark: 'Super Administrator with full access',
        },
        {
            roleName: 'General User',
            roleValue: 'user',
            status: 1,
            remark: 'Standard user with limited access',
        },
    ];

    for (const roleData of rolesData) {
        const existingRole = await roleService.findAll(); // Ideally findOneByValue but findAll is simple for now
        const exists = existingRole.find((r) => r.roleValue === roleData.roleValue);
        if (!exists) {
            await roleService.create(roleData);
            console.log(`Role created: ${roleData.roleName}`);
        } else {
            console.log(`Role already exists: ${roleData.roleName}`);
        }
    }

    // 2. Seed Admin User
    const adminUser: CreateUserDto = {
        username: 'admin',
        password: '123456', // Will be hashed by UserService
        nickname: 'Admin',
        email: 'admin@example.com',
        status: 1,
        deptId: null, // Optional
    };

    const existingUser = await userService.findOneByUsername(adminUser.username);
    if (!existingUser) {
        // We need to assign the admin role to this user
        // Since UserService.create doesn't handle role assignment directly in our current implementation (it might, let's check),
        // we might need to update it after creation or adjust CreateUserDto if we added role support there.
        // Checking UserService.create... it saves the user.
        // We need to manually associate the role.

        const createdUser = await userService.create(adminUser);
        console.log(`User created: ${adminUser.username}`);

        // Fetch the admin role
        const allRoles = await roleService.findAll();
        const adminRole = allRoles.find((r) => r.roleValue === 'admin');

        if (adminRole) {
            await userService.assignRoles(createdUser, [adminRole]);
            console.log(`Assigned role ${adminRole.roleName} to user ${createdUser.username}`);
        }
    } else {
        console.log(`User already exists: ${adminUser.username}`);
    }

    await app.close();
}

bootstrap();
