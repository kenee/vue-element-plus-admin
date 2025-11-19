import { Injectable, Logger } from '@nestjs/common';
import { UserService } from '../user/user.service';
import { JwtService } from '@nestjs/jwt';
import * as bcrypt from 'bcrypt';

@Injectable()
export class AuthService {
  private readonly logger = new Logger(AuthService.name);

  constructor(
    private userService: UserService,
    private jwtService: JwtService,
  ) { }

  async validateUser(username: string, pass: string): Promise<any> {
    this.logger.debug(`🔐 Validating user: ${username}`);
    const user = await this.userService.findOneByUsername(username);
    
    if (!user) {
      this.logger.warn(`❌ User not found: ${username}`);
      return null;
    }

    if (!user.password) {
      this.logger.error(`❌ User password is missing for: ${username}`);
      return null;
    }

    const isPasswordValid = await bcrypt.compare(pass, user.password);
    if (!isPasswordValid) {
      this.logger.warn(`❌ Invalid password for user: ${username}`);
      return null;
    }

    this.logger.log(`✅ User validated successfully: ${username}`);
    const { password, ...result } = user;
    return result;
  }

  async login(user: any) {
    const payload = { username: user.username, sub: user.id };
    return {
      access_token: this.jwtService.sign(payload),
      username: user.username,
      role: user.roles && user.roles.length > 0 ? user.roles[0].roleValue : '',
      roleId: user.roles && user.roles.length > 0 ? user.roles[0].id : '',
      id: user.id,
    };
  }
}
