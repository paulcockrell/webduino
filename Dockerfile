FROM node:20-bullseye AS node-build
WORKDIR /app

# Copy package.json and install NPM dependencies
COPY package.json ./
RUN npm install

# Copy the rest of the app for full build
COPY . .

# Build ClojureScript assets using Shadow CLJS
RUN npx shadow-cljs release app

# --- Runtime image with JVM ---
FROM clojure:temurin-21-tools-deps
WORKDIR /app

# Install Shadow CLJS globally
RUN npm install -g shadow-cljs

# Copy everything from node build
COPY --from=node-build /app /app

# Install Clojure dependencies
RUN clojure -P

# Run the app server
CMD ["clojure", "-M", "-m", "myapp.core"]
