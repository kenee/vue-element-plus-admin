import { Injectable } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository } from 'typeorm'
import { CreateDictionaryDto } from './dto/create-dictionary.dto'
import { UpdateDictionaryDto } from './dto/update-dictionary.dto'
import { Dictionary } from './entities/dictionary.entity'

@Injectable()
export class DictionaryService {
  constructor(
    @InjectRepository(Dictionary)
    private dictionaryRepository: Repository<Dictionary>
  ) {}

  create(createDictionaryDto: CreateDictionaryDto) {
    const dictionary = this.dictionaryRepository.create(createDictionaryDto)
    return this.dictionaryRepository.save(dictionary)
  }

  async findAll() {
    // Return all dictionaries with their items, formatted as a map if needed by frontend
    // The frontend expects:
    // {
    //   importance: [{ value: 0, label: 'tableDemo.commonly' }, ...],
    //   ...
    // }
    const dicts = await this.dictionaryRepository.find({ relations: ['items'] })
    const result: any = {}
    dicts.forEach((dict) => {
      result[dict.dictCode] = dict.items.map((item) => ({
        value: Number(item.value), // Ensure value is number if needed, or string based on requirement
        label: item.label
      }))
    })
    return result
  }

  async findOneByCode(code: string) {
    const dict = await this.dictionaryRepository.findOne({
      where: { dictCode: code },
      relations: ['items']
    })
    if (!dict) return []
    return dict.items.map((item) => ({
      label: item.label,
      value: Number(item.value)
    }))
  }

  async findOne(id: string) {
    return this.dictionaryRepository.findOne({ where: { id }, relations: ['items'] })
  }

  update(id: string, updateDictionaryDto: UpdateDictionaryDto) {
    return this.dictionaryRepository.update(id, updateDictionaryDto)
  }

  remove(id: string) {
    return this.dictionaryRepository.delete(id)
  }
}
