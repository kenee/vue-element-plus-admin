import { ExceptionFilter, Catch, ArgumentsHost, HttpException, HttpStatus } from '@nestjs/common'
import { Request, Response } from 'express'

@Catch()
export class AllExceptionsFilter implements ExceptionFilter {
  catch(exception: unknown, host: ArgumentsHost) {
    const ctx = host.switchToHttp()
    const response = ctx.getResponse<Response>()
    const request = ctx.getRequest<Request>()

    // 获取 HTTP 状态码（用于设置响应体中的 code 字段）
    const httpStatus =
      exception instanceof HttpException ? exception.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR

    // 获取错误消息
    const exceptionResponse =
      exception instanceof HttpException ? exception.getResponse() : 'Internal server error'

    // 提取错误消息文本
    const message =
      typeof exceptionResponse === 'string'
        ? exceptionResponse
        : (exceptionResponse as any).message || exceptionResponse

    // 始终返回 HTTP 200，通过响应体的 code 字段表示业务状态
    // 这样前端可以统一处理，通过 defaultResponseInterceptors 显示友好错误信息
    response.status(HttpStatus.OK).json({
      code: httpStatus, // 业务错误码（400, 404, 500 等）
      timestamp: new Date().toISOString(),
      path: request.url,
      message: message,
      data: null
    })
  }
}
