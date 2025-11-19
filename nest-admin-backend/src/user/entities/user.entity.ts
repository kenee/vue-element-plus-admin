import { Entity, PrimaryGeneratedColumn, Column, CreateDateColumn, UpdateDateColumn, ManyToMany, JoinTable } from 'typeorm';
import { Role } from '../../role/entities/role.entity';

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

    @ManyToMany(() => Role)
    @JoinTable({
        name: 'sys_user_role',
        joinColumn: { name: 'user_id', referencedColumnName: 'id' },
        inverseJoinColumn: { name: 'role_id', referencedColumnName: 'id' },
    })
    roles: Role[];
}
