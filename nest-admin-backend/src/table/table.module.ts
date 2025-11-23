import { Module } from '@nestjs/common'
import { TypeOrmModule } from '@nestjs/typeorm'
import { TableService } from './table.service'
import { TableController } from './table.controller'
import { TableExample } from './entities/table-example.entity'
import { CardExample } from './entities/card-example.entity'

@Module({
  imports: [TypeOrmModule.forFeature([TableExample, CardExample])],
  controllers: [TableController],
  providers: [TableService]
})
export class TableModule {}
