import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';

@Entity('sys_department')
export class Department {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column({ name: 'parent_id', nullable: true })
    parentId: string;

    @Column({ length: 100 })
    name: string;

    @Column({ type: 'int', default: 0 })
    sort: number;

    @Column({ type: 'tinyint', default: 1 })
    status: number;

    @Column({ length: 255, nullable: true })
    remark: string;

    @CreateDateColumn({ name: 'created_at' })
    createdAt: Date;

    @UpdateDateColumn({ name: 'updated_at' })
    updatedAt: Date;
}
