import { Controller, Get, Post, Body, Query } from '@nestjs/common'
import { TableService } from './table.service'
import { CreateTableDto } from './dto/create-table.dto'
import { QueryTableDto, QueryCardDto } from './dto/query-table.dto'
import { ApiTags, ApiOperation } from '@nestjs/swagger'

@ApiTags('table')
@Controller('table')
export class TableController {
  constructor(private readonly tableService: TableService) {}

  @ApiOperation({ summary: 'Get Example Table List' })
  @Get('example/list')
  getExampleList(@Query() query: QueryTableDto) {
    return this.tableService.findAll(query)
  }

  @ApiOperation({ summary: 'Get Tree Table List' })
  @Get('example/treeList')
  getTreeList(@Query() query: QueryTableDto) {
    return this.tableService.findTree(query)
  }

  @ApiOperation({ summary: 'Save Example Table' })
  @Post('example/save')
  saveExample(@Body() body: any) {
    return this.tableService.save(body)
  }

  @ApiOperation({ summary: 'Get Example Table Detail' })
  @Get('example/detail')
  getExampleDetail(@Query('id') id: string) {
    return this.tableService.findOne(id)
  }

  @ApiOperation({ summary: 'Delete Example Table' })
  @Post('example/delete')
  deleteExample(@Body() body: { ids: string[] }) {
    return this.tableService.remove(body.ids)
  }

  @ApiOperation({ summary: 'Get Card Table List' })
  @Get('card/list')
  getCardList(@Query() query: QueryCardDto) {
    return this.tableService.findCardList(query)
  }
}
