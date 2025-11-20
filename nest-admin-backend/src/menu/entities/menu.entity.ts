import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn } from 'typeorm'

@Entity('sys_menu')
export class Menu {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({ name: 'parent_id', nullable: true })
  parentId: string

  @Column({ length: 255, nullable: true })
  path: string

  @Column({ length: 255, nullable: true })
  component: string

  @Column({ length: 255, nullable: true })
  redirect: string

  @Column({ length: 100 })
  title: string

  @Column({ length: 100, nullable: true })
  name: string

  @Column({ length: 50, nullable: true })
  icon: string

  @Column({
    type: 'json',
    nullable: true,
    comment: 'Menu meta information (title, icon, alwaysShow, noCache, etc.)'
  })
  meta: Record<string, any>

  @Column({ type: 'tinyint', default: 0, comment: '0: Directory, 1: Menu, 2: Button' })
  type: number

  @Column({ type: 'tinyint', default: 1, comment: '0: disabled, 1: enabled' })
  status: number

  @Column({ length: 100, nullable: true })
  permission: string

  @Column({ type: 'int', default: 0 })
  sort: number

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date
}
