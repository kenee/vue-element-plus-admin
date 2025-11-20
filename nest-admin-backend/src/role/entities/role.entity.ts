import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  ManyToMany,
  JoinTable
} from 'typeorm'
import { Menu } from '../../menu/entities/menu.entity'

@Entity('sys_role')
export class Role {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({ name: 'role_name', length: 50 })
  roleName: string

  @Column({ name: 'role_value', length: 50, unique: true })
  roleValue: string

  @Column({ type: 'tinyint', default: 1 })
  status: number

  @Column({ length: 255, nullable: true })
  remark: string

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date

  @ManyToMany(() => Menu)
  @JoinTable({
    name: 'sys_role_menu',
    joinColumn: { name: 'role_id', referencedColumnName: 'id' },
    inverseJoinColumn: { name: 'menu_id', referencedColumnName: 'id' }
  })
  menus: Menu[]
}
