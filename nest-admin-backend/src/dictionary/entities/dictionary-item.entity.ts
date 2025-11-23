import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  ManyToOne,
  JoinColumn
} from 'typeorm'
import { Dictionary } from './dictionary.entity'

@Entity('sys_dictionary_item')
export class DictionaryItem {
  @PrimaryGeneratedColumn('uuid')
  id: string

  @Column({ name: 'dict_id' })
  dictId: string

  @Column({ length: 100 })
  label: string

  @Column({ length: 100 })
  value: string

  @Column({ type: 'int', default: 0 })
  sort: number

  @Column({ type: 'tinyint', default: 1 })
  status: number

  @ManyToOne(() => Dictionary, (dictionary) => dictionary.items)
  @JoinColumn({ name: 'dict_id' })
  dictionary: Dictionary

  @CreateDateColumn({ name: 'created_at' })
  createdAt: Date

  @UpdateDateColumn({ name: 'updated_at' })
  updatedAt: Date
}
