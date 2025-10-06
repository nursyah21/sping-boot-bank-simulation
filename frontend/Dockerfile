FROM node:24-alpine

WORKDIR /app

COPY package.json .
COPY package-lock.json .
COPY app app
COPY .env .
COPY .eslintrc.json .
COPY tsconfig.json .
COPY next.config.mjs .

RUN npm install
RUN npm run lint
RUN npm run build

EXPOSE 3000

ENTRYPOINT [ "npm", "run", "start" ]