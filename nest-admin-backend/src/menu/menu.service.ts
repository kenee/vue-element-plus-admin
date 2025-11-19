import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateMenuDto } from './dto/create-menu.dto';
import { UpdateMenuDto } from './dto/update-menu.dto';
import { Menu } from './entities/menu.entity';

@Injectable()
export class MenuService {
  constructor(
    @InjectRepository(Menu)
    private menuRepository: Repository<Menu>,
  ) { }

  create(createMenuDto: CreateMenuDto) {
    const menu = this.menuRepository.create(createMenuDto);
    return this.menuRepository.save(menu);
  }

  getRoutes() {
    return [
      {
        path: '/dashboard',
        component: '#',
        redirect: '/dashboard/analysis',
        name: 'Dashboard',
        meta: {
          title: 'router.dashboard',
          icon: 'vi-ant-design:dashboard-filled',
          alwaysShow: true,
        },
        children: [
          {
            path: 'analysis',
            component: 'views/Dashboard/Analysis',
            name: 'Analysis',
            meta: {
              title: 'router.analysis',
              noCache: true,
              affix: true,
            },
          },
          {
            path: 'workplace',
            component: 'views/Dashboard/Workplace',
            name: 'Workplace',
            meta: {
              title: 'router.workplace',
              noCache: true,
              affix: true,
            },
          },
        ],
      },
      {
        path: '/authorization',
        component: '#',
        redirect: '/authorization/user',
        name: 'Authorization',
        meta: {
          title: 'router.authorization',
          icon: 'vi-eos-icons:role-binding',
          alwaysShow: true,
        },
        children: [
          {
            path: 'department',
            component: 'views/Authorization/Department/Department',
            name: 'Department',
            meta: {
              title: 'router.department',
            },
          },
          {
            path: 'user',
            component: 'views/Authorization/User/User',
            name: 'User',
            meta: {
              title: 'router.user',
            },
          },
          {
            path: 'menu',
            component: 'views/Authorization/Menu/Menu',
            name: 'Menu',
            meta: {
              title: 'router.menuManagement',
            },
          },
          {
            path: 'role',
            component: 'views/Authorization/Role/Role',
            name: 'Role',
            meta: {
              title: 'router.role',
            },
          },
        ],
      },
    ];
  }

  findAll() {
    return this.menuRepository.find();
  }

  findOne(id: string) {
    return this.menuRepository.findOneBy({ id });
  }

  update(id: string, updateMenuDto: UpdateMenuDto) {
    return this.menuRepository.update(id, updateMenuDto);
  }

  remove(id: string) {
    return this.menuRepository.delete(id);
  }
}
