import { Controller, Get } from '@nestjs/common';
import { WorkplaceService } from './workplace.service';
import { ApiTags } from '@nestjs/swagger';

@ApiTags('workplace')
@Controller('workplace')
export class WorkplaceController {
  constructor(private readonly workplaceService: WorkplaceService) { }

  @Get('total')
  getTotal() {
    return this.workplaceService.getTotal();
  }

  @Get('project')
  getProject() {
    return this.workplaceService.getProject();
  }

  @Get('dynamic')
  getDynamic() {
    return this.workplaceService.getDynamic();
  }

  @Get('team')
  getTeam() {
    return this.workplaceService.getTeam();
  }

  @Get('radar')
  getRadar() {
    return this.workplaceService.getRadar();
  }
}
