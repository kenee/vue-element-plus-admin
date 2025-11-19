import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ConfigService } from '@nestjs/config';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { TransformInterceptor } from './common/interceptors/transform.interceptor';
import { LoggingInterceptor } from './common/interceptors/logging.interceptor';
import { AllExceptionsFilter } from './common/filters/all-exceptions.filter';

async function bootstrap() {
    const app = await NestFactory.create(AppModule, {
        logger: ['log', 'error', 'warn', 'debug', 'verbose'],
    });

    const configService = app.get(ConfigService);
    const port = Number(configService.get('PORT')) || 3000;
    const apiPrefix = configService.get('API_PREFIX') || 'api';
    const corsOrigin = configService.get('CORS_ORIGIN') || 'http://localhost:4000';

    // 设置全局路由前缀
    app.setGlobalPrefix(apiPrefix);

    const config = new DocumentBuilder()
        .setTitle('Nest Admin API')
        .setDescription('The Nest Admin API description')
        .setVersion('1.0')
        .addBearerAuth()
        .build();
    const document = SwaggerModule.createDocument(app, config);
    SwaggerModule.setup('api-docs', app, document);

    // Enable CORS for frontend access
    app.enableCors({
        origin: corsOrigin,
        credentials: true,
    });
    
    // 日志拦截器应该在转换拦截器之前，以便记录原始请求
    app.useGlobalInterceptors(new LoggingInterceptor());
    app.useGlobalInterceptors(new TransformInterceptor());
    app.useGlobalFilters(new AllExceptionsFilter());
    
    await app.listen(port);
    console.log(`🚀 Application is running on: http://localhost:${port}`);
    console.log(`📚 Swagger API docs: http://localhost:${port}/api-docs`);
}
bootstrap();
