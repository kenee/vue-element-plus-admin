# AGENTS.md

This document provides essential information for AI agents working in this Vue 3 + Element Plus admin template.

## Build & Development Commands

```bash
# Development
pnpm run dev                          # Start dev server (mode: base)
pnpm run ts:check                     # TypeScript type check

# Build
pnpm run build:pro                    # Production build
pnpm run build:dev                    # Dev build
pnpm run build:test                   # Test build
pnpm run build:gitee                  # Gitee build

# Preview
pnpm run serve:pro                    # Preview production build
pnpm run serve:dev                    # Preview dev build
pnpm run serve:test                   # Preview test build

# Linting & Formatting
pnpm run lint:eslint                  # ESLint with auto-fix
pnpm run lint:format                  # Prettier format
pnpm run lint:style                   # Stylelint with auto-fix
```

**Note**: This project does not have a test framework configured. No single test command available.

---

## Code Style Guidelines

### Imports

- Use named imports from `vue` composition API: `import { ref, computed } from 'vue'`
- Use `import type { ... }` for type-only imports
- Path alias: `@/*` maps to `src/*`
- Third-party: `import { ElMessage } from 'element-plus'`
- Local: `import { xxxApi } from '@/api/xxx'`

### Formatting (Prettier)

- **Print width**: 100, **Indentation**: 2 spaces, **No tabs**
- **Semicolons**: No semicolons, **Quotes**: Single quotes
- **Trailing commas**: None, **Arrow functions**: Always use parentheses

### TypeScript

- **Target**: ESNext, **Strict mode**: Enabled (except `noImplicitAny` is disabled)
- **Unused variables**: `noUnusedLocals` and `noUnusedParameters` enabled
- **Module**: ESNext, **JSX**: Preserve with Vue import source
- API types in `src/api/xxx/types.ts`, global types in `types/global.d.ts`
- Generic response: `IResponse<T>` with `code` and `data` fields

### Naming Conventions

- **Components**: PascalCase files (e.g., `DefaultTable.vue`)
- **Hooks**: `useXxx` prefix (e.g., `useTable.ts`)
- **Store functions**: `useXxxStore` pattern (e.g., `useUserStore`)
- **API functions**: `xxxApi` suffix (e.g., `loginApi`)
- **Utilities**: camelCase (e.g., `humpToUnderline`)
- **Interfaces**: PascalCase (e.g., `UserLoginType`)

### Vue Component Structure

Use Composition API with `<script setup>`:

```vue
<script setup lang="ts">
import { ref, computed } from 'vue'

const loading = ref(true)
const tableData = ref<any[]>([])
</script>

<template>
  <div class="container">...</div>
</template>

<style lang="less" scoped>
.container {
  padding: 20px;
}
</style>
```

### Error Handling

- API errors handled by axios interceptors in `src/axios/service.ts`
- Local: `.catch(() => {})` to suppress errors
- User notifications: `ElMessage.error()` / `ElMessage.success()`
- Confirmations: `ElMessageBox.confirm()`

### Pinia Store Structure

Store modules in `src/store/modules/`:

```ts
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({ token: '' }),
  getters: {
    getToken(): string {
      return this.token
    }
  },
  actions: {
    setToken(token: string) {
      this.token = token
    }
  },
  persist: true
})
```

### API Layer Pattern

- Request config in `src/axios/`, API modules in `src/api/xxx/index.ts`
- Types in `src/api/xxx/types.ts`, Standard: `Promise<IResponse<T>>`

```ts
export const getUserListApi = (
  params: UserListParams
): Promise<IResponse<{ list: UserType[]; total: number }>> => {
  return request.get({ url: '/api/user', params })
}
```

### Component Organization

- Reusable components in `src/components/ComponentName/`
- Each has `src/ComponentName.vue` and `index.ts`
- Use `withInstall` helper for registration

```ts
import Table from './src/Table.vue'
import { withInstall } from '@/utils'
export { Table }
export type { TableColumn } from './src/types'
withInstall(Table)
```

### Utilities & Styling

- Pure functions in `src/utils/` (e.g., `humpToUnderline`, `formatTime`, `toAnyString`)
- CSS: Less preprocessor, scoped styles, UnoCSS utilities
- Element Plus theme via CSS variables, `@{adminNamespace}` prefix

### Linting

- ESLint flat config (ESLint 9+), Prettier enforced as error
- Many TS rules disabled (`no-explicit-any`, `no-unused-vars`, `ban-ts-comment`)
- Vue rules mostly disabled, **Always run `pnpm run lint:format` before committing**

### File Structure

```
src/
├── api/              # API modules (index.ts, types.ts)
├── components/       # Reusable components
├── views/            # Page components
├── store/            # Pinia stores (modules/)
├── hooks/            # Composition API hooks (web/)
├── router/           # Vue Router config
├── utils/            # Utility functions
└── types/            # Global type definitions
```

### Commit Convention

- `feat`: New features, `fix`: Bug fixes, `docs`: Documentation
- `style`: Format/style, `refactor`: Refactoring, `perf`: Performance
- `test`: Add tests, `build`: Build/config, `chore`: Maintenance, `revert`: Revert

### Important Notes

1. **No test framework** - Do not add tests unless explicitly requested
2. **ESLint is relaxed** - Use TypeScript for safety, not lint rules
3. **Prettier is enforced** - Code must be formatted before committing
4. **Use path alias `@/*`** for imports from `src/`
5. **Pinia stores** use persistence plugin - mark with `persist: true`
6. **TypeScript types** should be defined in separate `types.ts` files for APIs
7. **Element Plus** is the primary UI library - use its components first
8. **Composition API** with `<script setup>` is the standard approach

---

## Python Backend (FastAPI)

The project includes a FastAPI backend located in `python-admin-backend/` directory, using **uv** for dependency management.

### Quick Setup

```bash
cd python-admin-backend

# Create virtual environment
uv venv
source .venv/bin/activate  # On Windows: .venv\Scripts\activate

# Install dependencies
uv pip install -e .

# Run dev server
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

### Dependency Management with uv

- **Package manager**: uv (modern Python package manager, 10-100x faster than pip)
- **Config file**: `pyproject.toml` (uv standard)
- **Legacy support**: `requirements.txt` still available for compatibility
- **Python version**: Requires Python 3.8+

### Common uv Commands

```bash
# Create virtual environment
uv venv

# Install project dependencies
uv pip install -e .

# Add new dependency
uv pip add package-name

# List installed packages
uv pip list

# Update dependencies
uv pip install -e . --upgrade

# Sync with lockfile (if using uv.lock)
uv pip sync uv.lock
```

### Project Structure

```
python-admin-backend/
├── app/
│   ├── api/              # API routes (auth.py, user.py, etc.)
│   ├── core/             # Core config (config.py, security.py)
│   ├── db/               # Database setup
│   ├── models/           # SQLAlchemy models
│   ├── schemas/          # Pydantic schemas
│   └── main.py           # FastAPI app entry
├── alembic/              # Database migrations
├── tests/                # Tests
├── pyproject.toml        # uv dependency config
├── requirements.txt      # Legacy requirements (for compatibility)
└── .env.example          # Environment variables template
```

### Tech Stack

- **Web Framework**: FastAPI 0.109.0
- **ORM**: SQLAlchemy 2.0.25
- **Migrations**: Alembic
- **Authentication**: JWT (python-jose)
- **Validation**: Pydantic 2.5.3
- **ASGI Server**: Uvicorn

### Environment Setup

1. Copy environment variables: `cp .env.example .env`
2. Configure DATABASE_URL, SECRET_KEY, etc.
3. Run migrations: `alembic upgrade head`
4. Start server: `uvicorn app.main:app --reload`

### Database Migration

```bash
# Generate migration
alembic revision --autogenerate -m "description"

# Apply migrations
alembic upgrade head

# Rollback
alembic downgrade -1
```

### API Documentation

- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

### Code Style

- **Formatter**: black (line-length: 100)
- **Type checker**: mypy
- **Python version**: 3.8+

### Why uv?

- **Speed**: 10-100x faster dependency resolution than pip
- **Locking**: Automatic dependency lockfile generation
- **Compatibility**: Fully compatible with pip workflow
- **Modern**: Uses pyproject.toml standard
- **Built-in venv**: Virtual environment management included

### Python Backend Notes

1. **uv is required** for dependency management (faster, modern)
2. **Virtual environment** must be activated before running commands
3. **Environment variables** in `.env` are required for database and auth
4. **Alembic migrations** must be run before first start
5. **pyproject.toml** is the primary dependency file (requirements.txt for compatibility)
