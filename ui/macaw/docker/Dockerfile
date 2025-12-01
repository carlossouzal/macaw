FROM node:20-alpine

WORKDIR /app

# Install deps with caching
COPY package.json package-lock.json* pnpm-lock.yaml* yarn.lock* ./
RUN if [ -f pnpm-lock.yaml ]; then \
      npm i -g pnpm && pnpm install --frozen-lockfile; \
    elif [ -f yarn.lock ]; then \
      yarn install --frozen-lockfile; \
    else \
      npm ci; \
    fi

# Copy source
COPY . .

# Expose the dev server port
EXPOSE 3000

# Use Vite dev server; allow external access from container
ENV HOST=0.0.0.0
CMD [ "npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "3000" ]