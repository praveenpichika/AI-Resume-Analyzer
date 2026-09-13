module.exports = (req, res) => {
  const { method, url = '/' } = req;
  const path = url.split('?')[0];

  const sendJson = (statusCode, payload) => {
    res.status(statusCode).setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(payload));
  };

  if (method === 'GET' && path === '/api/health') {
    return sendJson(200, { status: 'ok', message: 'Resume Analyzer Vercel API is running.' });
  }

  if (method === 'GET' && path === '/api/logout') {
    return sendJson(200, { success: true, message: 'Logged out' });
  }

  if (method === 'GET' && path === '/api/check-session') {
    return sendJson(200, { authenticated: true, message: 'Session active' });
  }

  if (method === 'GET' && path === '/api/profile-data') {
    return sendJson(200, {
      name: 'Demo User',
      email: 'demo@resumeanalyzer.app',
      totalResumes: 3,
      averageScore: 84
    });
  }

  if (method === 'GET' && path === '/api/all') {
    return sendJson(200, [
      { id: 1, skills: 'Java, Spring Boot', atsScore: 88 },
      { id: 2, skills: 'React, Node.js', atsScore: 79 }
    ]);
  }

  if (method === 'GET' && path === '/api/resumes') {
    return sendJson(200, [
      { id: 1, fileName: 'demo-resume.pdf', score: 88 },
      { id: 2, fileName: 'backend-engineer.pdf', score: 79 }
    ]);
  }

  if (method === 'POST' && path === '/api/resume/upload') {
    return sendJson(200, {
      atsScore: 86,
      jobMatch: 91,
      detectedSkills: 'Java, Spring Boot, REST APIs',
      missingSkills: 'AWS, Docker, Kubernetes',
      suggestions: 'Add cloud deployment knowledge.\nInclude container orchestration experience.\nHighlight backend architecture work.',
      summary: 'A strong backend-focused candidate profile with solid Java and API experience.'
    });
  }

  if (method === 'POST' && path === '/api/resume/pdf') {
    const pdf = '%PDF-1.4\n1 0 obj<< /Type /Catalog /Pages 2 0 R >>endobj\n2 0 obj<< /Type /Pages /Kids [3 0 R] /Count 1 >>endobj\n3 0 obj<< /Type /Page /Parent 2 0 R /MediaBox [0 0 300 144] /Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>endobj\n4 0 obj<< /Length 44 >>stream\nBT /F1 18 Tf 20 100 Td (Resume Report) Tj ET\nendstream\nendobj\n5 0 obj<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>endobj\nxref\n0 6\n0000000000 65535 f \n0000000010 00000 n \n0000000062 00000 n \n0000000119 00000 n \n0000000206 00000 n \n0000000307 00000 n \ntrailer<< /Size 6 /Root 1 0 R >>\nstartxref\n0\n%%EOF';
    res.status(200).setHeader('Content-Type', 'application/pdf');
    res.end(pdf);
    return;
  }

  return sendJson(404, { error: 'Route not found' });
};
