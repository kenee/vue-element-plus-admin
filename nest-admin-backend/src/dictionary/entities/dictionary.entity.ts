import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  OneToMany
} from 'typeorm'
import { DictionaryItem } from './dictionary-item.entity'

@Entity('sys_dictionary')
export class Dictionary {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({ name: 'dict_name', length: 100 })
  dictName: string

  @Column({ name: 'dict_code', length: 100, unique: true })
  dictCode: string

  @Column({ type: 'tinyint', default: 1 })
  status: number

  @Column({ length: 255, nullable: true })
  remark: string

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date

  @OneToMany(() => DictionaryItem, (item) => item.dictionary)
  items: DictionaryItem[]
}
