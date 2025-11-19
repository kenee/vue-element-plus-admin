import {
    Injectable,
    NestInterceptor,
    ExecutionContext,
    CallHandler,
    Logger,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Request, Response } from 'express';

@Injectable()
export class LoggingInterceptor implements NestInterceptor {
    private readonly logger = new Logger('HTTP');

    intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
        const ctx = context.switchToHttp();
        const request = ctx.getRequest<Request>();
        const response = ctx.getResponse<Response>();
        const { method, url, body, query, params, headers } = request;
        const userAgent = headers['user-agent'] || '';
        const ip = request.ip || request.socket.remoteAddress || '';

        const now = Date.now();

        // 记录请求信息
        this.logger.log(
            `➡️  ${method} ${url} - ${ip} - ${userAgent}`,
        );

        // 如果有请求体，记录请求体（排除敏感信息）
        if (body && Object.keys(body).length > 0) {
            const sanitizedBody = this.sanitizeBody(body);
            this.logger.debug(`📦 Request Body: ${JSON.stringify(sanitizedBody, null, 2)}`);
        }

        // 如果有查询参数，记录查询参数
        if (query && Object.keys(query).length > 0) {
            this.logger.debug(`🔍 Query Params: ${JSON.stringify(query, null, 2)}`);
        }

        return next.handle().pipe(
            tap({
                next: (data) => {
                    const delay = Date.now() - now;
                    const statusCode = response.statusCode;
                    const statusEmoji = statusCode >= 400 ? '❌' : '✅';
                    
                    this.logger.log(
                        `${statusEmoji} ${method} ${url} ${statusCode} - ${delay}ms`,
                    );
                },
                error: (error) => {
                    const delay = Date.now() - now;
                    const statusCode = error.status || 500;
                    
                    this.logger.error(
                        `❌ ${method} ${url} ${statusCode} - ${delay}ms - ${error.message}`,
                    );
                },
            }),
        );
    }

    // 清理敏感信息（如密码）
    private sanitizeBody(body: any): any {
        const sanitized = { ...body };
        const sensitiveFields = ['password', 'oldPassword', 'newPassword', 'token', 'accessToken', 'refreshToken'];
        
        sensitiveFields.forEach(field => {
            if (sanitized[field]) {
                sanitized[field] = '***';
            }
        });
        
        return sanitized;
    }
}

