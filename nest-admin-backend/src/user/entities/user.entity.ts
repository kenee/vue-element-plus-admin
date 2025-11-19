import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn } from 'typeorm';

@Entity('sys_user')
export class User {
    @PrimaryGeneratedColumn('uuid')
    id: string;

    @Column({ length: 50, unique: true })
    username: string;

    @Column({ length: 100, select: false }) // Don't return password by default
    password: string;

    @Column({ length: 50, nullable: true })
    nickname: string;

    @Column({ name: 'dept_id', nullable: true })
    deptId: string;

    @Column({ length: 100, nullable: true })
    email: string;

    @Column({ type: 'tinyint', default: 1 })
    status: number;

    @CreateDateColumn({ name: 'created_at' })
    createdAt: Date;

    @UpdateDateColumn({ name: 'updated_at' })
    updatedAt: Date;
}
