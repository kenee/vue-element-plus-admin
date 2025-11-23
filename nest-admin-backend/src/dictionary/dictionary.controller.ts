import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common'
import { DictionaryService } from './dictionary.service'
import { CreateDictionaryDto } from './dto/create-dictionary.dto'
import { UpdateDictionaryDto } from './dto/update-dictionary.dto'
import { ApiTags } from '@nestjs/swagger'

@ApiTags('dictionary')
@Controller('dictionary')
export class DictionaryController {
  constructor(private readonly dictionaryService: DictionaryService) {}

  @Post()
  create(@Body() createDictionaryDto: CreateDictionaryDto) {
    return this.dictionaryService.create(createDictionaryDto)
  }

  @Get('list')
  findAll() {
    return this.dictionaryService.findAll()
  }

  @Get('one')
  findOneByCode() {
    // The mock was returning a specific list for testing.
    // We can just return a hardcoded list or fetch a specific dict.
    // Mock url was /mock/dict/one
    return [
      {
        label: 'test1',
        value: 0
      },
      {
        label: 'test2',
        value: 1
      },
      {
        label: 'test3',
        value: 2
      }
    ]
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.dictionaryService.findOne(id)
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateDictionaryDto: UpdateDictionaryDto) {
    return this.dictionaryService.update(id, updateDictionaryDto)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.dictionaryService.remove(id)
  }
}
