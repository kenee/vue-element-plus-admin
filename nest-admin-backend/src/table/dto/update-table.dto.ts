import { PartialType } from '@nestjs/swagger'
import { CreateTableDto } from './create-table.dto'
import { IsString, IsNotEmpty } from 'class-validator'

export class UpdateTableDto extends PartialType(CreateTableDto) {
  @IsString()
  @IsNotEmpty()
  id: string
}
