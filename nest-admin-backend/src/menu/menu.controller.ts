import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  UseGuards,
  Request
} from '@nestjs/common'
import { MenuService } from './menu.service'
import { CreateMenuDto } from './dto/create-menu.dto'
import { UpdateMenuDto } from './dto/update-menu.dto'
import { ApiTags, ApiBearerAuth } from '@nestjs/swagger'
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard'

@ApiTags('menu')
@Controller('menu')
export class MenuController {
  constructor(private readonly menuService: MenuService) {}

  @Post()
  create(@Body() createMenuDto: CreateMenuDto) {
    return this.menuService.create(createMenuDto)
  }

  @Get('routes')
  @UseGuards(JwtAuthGuard)
  @ApiBearerAuth()
  async getRoutes(@Request() req: any) {
    // 从 JWT token 中获取用户信息
    const userId = req.user?.userId
    return this.menuService.getRoutesByUser(userId)
  }

  @Get()
  findAll() {
    return this.menuService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.menuService.findOne(id)
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateMenuDto: UpdateMenuDto) {
    return this.menuService.update(id, updateMenuDto)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.menuService.remove(id)
  }
}
