import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  Tree,
  TreeChildren,
  TreeParent
} from 'typeorm'

@Entity('sys_table_example')
@Tree('closure-table')
export class TableExample {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({ length: 100 })
  author: string

  @Column({ length: 200 })
  title: string

  @Column({ type: 'text', nullable: true })
  content: string

  @Column({ type: 'int', default: 1 })
  importance: number

  @Column({ name: 'display_time', type: 'datetime' })
  displayTime: Date

  @Column({ type: 'int', default: 0 })
  pageviews: number

  @Column({ name: 'image_uri', length: 500, nullable: true })
  imageUri: string

  @TreeChildren()
  children: TableExample[]

  @TreeParent()
  parent: TableExample

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date
}
