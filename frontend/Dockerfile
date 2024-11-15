# Step 1: Build the React application
FROM node:18 AS builder

WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire project (including source code)
COPY . .

# Build the React app (production build)
RUN npm run build

# Step 2: Serve the built app with Nginx
FROM nginx:alpine AS runtime

# Copy the build output from the builder stage to the nginx html directory
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80 (default port for nginx)
EXPOSE 80

# Serve the app using nginx (this is the default behavior of nginx)
CMD ["nginx", "-g", "daemon off;"]
