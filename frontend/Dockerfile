FROM node:24-alpine

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy source code
COPY . .

# Expose Vite dev server port
EXPOSE 5173

# Start dev server with host flag to allow external access
CMD ["npm", "run", "dev", "--", "--host"]