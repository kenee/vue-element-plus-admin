import { Module } from '@nestjs/common'
import { TypeOrmModule } from '@nestjs/typeorm'
import { RoleService } from './role.service'
import { RoleController } from './role.controller'
import { Role } from './entities/role.entity'
import { Menu } from '../menu/entities/menu.entity'
import { User } from '../user/entities/user.entity'
import { MenuModule } from '../menu/menu.module'

@Module({
  imports: [TypeOrmModule.forFeature([Role, Menu, User]), MenuModule],
  controllers: [RoleController],
  providers: [RoleService],
  exports: [RoleService]
})
export class RoleModule {}
