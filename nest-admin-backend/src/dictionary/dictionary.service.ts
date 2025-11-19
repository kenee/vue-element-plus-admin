import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { CreateDictionaryDto } from './dto/create-dictionary.dto';
import { UpdateDictionaryDto } from './dto/update-dictionary.dto';
import { Dictionary } from './entities/dictionary.entity';

@Injectable()
export class DictionaryService {
  constructor(
    @InjectRepository(Dictionary)
    private dictionaryRepository: Repository<Dictionary>,
  ) { }

  create(createDictionaryDto: CreateDictionaryDto) {
    const dictionary = this.dictionaryRepository.create(createDictionaryDto);
    return this.dictionaryRepository.save(dictionary);
  }

  findAll() {
    return this.dictionaryRepository.find();
  }

  findOne(id: string) {
    return this.dictionaryRepository.findOneBy({ id });
  }

  update(id: string, updateDictionaryDto: UpdateDictionaryDto) {
    return this.dictionaryRepository.update(id, updateDictionaryDto);
  }

  remove(id: string) {
    return this.dictionaryRepository.delete(id);
  }
}
