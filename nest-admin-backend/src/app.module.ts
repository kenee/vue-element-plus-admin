import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UserModule } from './user/user.module';
import { AuthModule } from './auth/auth.module';
import { RoleModule } from './role/role.module';
import { MenuModule } from './menu/menu.module';
import { DepartmentModule } from './department/department.module';
import { DictionaryModule } from './dictionary/dictionary.module';
import { AnalysisModule } from './analysis/analysis.module';
import { WorkplaceModule } from './workplace/workplace.module';

@Module({
    imports: [
        // 配置模块 - 全局可用
        // 根据 NODE_ENV 环境变量加载对应的 .env 文件
        ConfigModule.forRoot({
            isGlobal: true,
            envFilePath: [
                `.env.${process.env.NODE_ENV || 'dev'}`,
                '.env',
            ],
        }),
        // 数据库配置
        TypeOrmModule.forRootAsync({
            imports: [ConfigModule],
            useFactory: (configService: ConfigService) => {
                const dbType = configService.get('DB_TYPE') || 'mysql';
                const dbHost = String(configService.get('DB_HOST') || 'localhost');
                const dbPort = Number(configService.get('DB_PORT')) || 3307;
                const dbUsername = String(configService.get('DB_USERNAME') || 'root');
                const dbPassword = String(configService.get('DB_PASSWORD') || 'root');
                const dbDatabase = String(configService.get('DB_DATABASE') || 'nest_admin');
                const nodeEnv = String(configService.get('NODE_ENV') || 'development');
                
                return {
                    type: dbType as any,
                    host: dbHost,
                    port: dbPort,
                    username: dbUsername,
                    password: dbPassword,
                    database: dbDatabase,
                    autoLoadEntities: true,
                    synchronize: nodeEnv !== 'production',
                };
            },
            inject: [ConfigService],
        }),
        UserModule,
        AuthModule,
        RoleModule,
        MenuModule,
        DepartmentModule,
        DictionaryModule,
        AnalysisModule,
        WorkplaceModule,
    ],
    controllers: [],
    providers: [],
})
export class AppModule { }
