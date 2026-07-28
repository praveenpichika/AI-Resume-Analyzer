const fs = require('fs');
const path = require('path');

const sourceDir = path.join(__dirname, '..', 'src', 'main', 'resources', 'static');
const targetDir = path.join(__dirname, '..', 'public');

if (!fs.existsSync(sourceDir)) {
  console.error('Static source directory not found:', sourceDir);
  process.exit(1);
}

if (!fs.existsSync(targetDir)) {
  fs.mkdirSync(targetDir, { recursive: true });
}

const entries = fs.readdirSync(sourceDir, { withFileTypes: true });
for (const entry of entries) {
  const srcPath = path.join(sourceDir, entry.name);
  const destPath = path.join(targetDir, entry.name);
  if (entry.isDirectory()) {
    fs.cpSync(srcPath, destPath, { recursive: true });
  } else {
    fs.copyFileSync(srcPath, destPath);
  }
}

console.log('Prepared static assets for Vercel deployment.');
