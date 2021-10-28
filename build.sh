#!/bin/sh
cd frontend
npm install
npm run build
cd ..
cp -r frontend/build/* src/main/resources/static/
