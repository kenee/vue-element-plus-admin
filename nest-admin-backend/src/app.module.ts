import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserModule } from './user/user.module';
import { AuthModule } from './auth/auth.module';
import { RoleModule } from './role/role.module';
import { MenuModule } from './menu/menu.module';
import { DepartmentModule } from './department/department.module';
import { DictionaryModule } from './dictionary/dictionary.module';
import { AnalysisModule } from './analysis/analysis.module';
import { WorkplaceModule } from './workplace/workplace.module';

@Module({
    imports: [
        TypeOrmModule.forRoot({
            type: 'mysql',
            host: 'localhost',
            port: 3307,
            username: 'root',
            password: 'root',
            database: 'nest_admin',
            autoLoadEntities: true,
            synchronize: true,
        }),
        UserModule,
        AuthModule,
        RoleModule,
        MenuModule,
        DepartmentModule,
        DictionaryModule,
        AnalysisModule,
        WorkplaceModule,
    ],
    controllers: [],
    providers: [],
})
export class AppModule { }
