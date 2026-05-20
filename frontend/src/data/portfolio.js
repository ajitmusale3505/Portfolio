export const profile = {
  name: 'Ajit Musale',
  shortName: 'Ajit',
  initials: 'AM',
  title: 'Full Stack Developer',
  location: 'Pune, India',
  email: 'ajitmusale645@gmail.com',
  github: 'https://github.com/ajitmusale3505',
  linkedin: 'https://www.linkedin.com/in/musale-ajit/',
  resume: '/assets/Ajit_Full-Stack_Resume.pdf',
  photo: '/assets/profile.png',
  roles: ['Java Developer', 'Spring Boot Developer', 'Full Stack Developer', 'Backend Developer'],
  bio:
    'Final-year Computer Engineering student from Savitribai Phule Pune University with hands-on full-stack development experience at Robo Waves Technologies. I build Java and Spring Boot applications with REST APIs, React interfaces, and SQL-backed workflows that are structured for real use, not just demos.',
}

export const metrics = [
  { value: '8.1', label: 'CGPA' },
  { value: '90.80%', label: 'TCS iON Java Hands-On' },
  { value: '4+', label: 'Production-style Projects' },
  { value: '2', label: 'Hackathons' },
]

export const techLogos = [
  { name: 'Java', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg', tone: '#f89820' },
  { name: 'Spring', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg', tone: '#6db33f' },
  { name: 'React', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg', tone: '#61dafb' },
  { name: 'PostgreSQL', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg', tone: '#336791' },
  { name: 'JavaScript', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg', tone: '#f7df1e' },
  { name: 'Tailwind', src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/tailwindcss/tailwindcss-original.svg', tone: '#38bdf8' },
]

export const skills = [
  {
    group: 'Frontend',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg',
    level: 82,
    summary: 'Interfaces, state-driven UI, responsive layouts and API integration.',
    items: ['HTML', 'CSS', 'JavaScript', 'React.js', 'Tailwind CSS', 'JSON', 'XML'],
  },
  {
    group: 'Backend',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
    level: 88,
    summary: 'Spring Boot, MVC patterns, REST APIs, service layers and server-side workflows.',
    items: ['Java', 'Spring Boot', 'Spring MVC', 'Hibernate/JPA', 'Servlets', 'REST APIs'],
  },
  {
    group: 'Database',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg',
    level: 78,
    summary: 'SQL schema design, CRUD operations, JDBC and relational data modeling.',
    items: ['PostgreSQL', 'MySQL', 'JDBC', 'Relational Design', 'CRUD Workflows'],
  },
  {
    group: 'Tools',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg',
    level: 74,
    summary: 'Daily development workflow with IDEs, Maven, GitHub and deployment tooling.',
    items: ['STS', 'Maven', 'Eclipse', 'VS Code', 'Git', 'GitHub', 'Vercel'],
  },
  {
    group: 'Core Concepts',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
    level: 80,
    summary: 'OOP, debugging, layered architecture, authentication flow and problem solving.',
    items: ['OOP', 'MVC Architecture', 'Authentication Flow', 'API Integration', 'Debugging'],
  },
]

export const projects = [
  {
    name: 'Food Delivery Management System',
    type: 'Internship Project',
    description:
      'A full-stack food ordering workflow built with Spring Boot and MVC patterns, covering restaurant listings, menu management, cart flow, order placement, and admin-side operations.',
    role: 'Solo developer during Robo Waves Technologies internship',
    stack: ['Spring Boot', 'Spring MVC', 'REST API', 'JSP', 'Servlets', 'PostgreSQL'],
    github: 'https://github.com/ajitmusale3505/fooddelivery',
    live: '',
    preview: 'food',
    impact: ['Role-based flows', 'Order lifecycle handling', 'Structured MVC layers', 'Database-backed CRUD'],
  },
  {
    name: 'Internet Banking System',
    type: 'Secure Backend Workflow',
    description:
      'A banking application concept focused on account management, fund transfers, transaction history, authentication, and admin controls using a Java web stack.',
    role: 'Full-stack implementation and database workflow design',
    stack: ['Spring Boot', 'Spring MVC', 'REST API', 'JSP', 'Servlets', 'JDBC', 'PostgreSQL'],
    github: '',
    live: '',
    preview: 'banking',
    impact: ['Account dashboard', 'Transaction records', 'Authentication flow', 'Admin monitoring'],
  },
  {
    name: 'E-Commerce Platform',
    type: 'Full Stack Application',
    description:
      'A commerce platform architecture with product catalog, cart, checkout-ready order flow, authentication, and admin product management.',
    role: 'Full-stack developer',
    stack: ['React.js', 'Spring Boot', 'REST API', 'PostgreSQL', 'Tailwind CSS'],
    github: '',
    live: '',
    preview: 'commerce',
    impact: ['Product catalog', 'Cart flow', 'Admin inventory', 'API-driven UI'],
  },
  {
    name: 'Developer Portfolio Dashboard',
    type: 'Frontend + API Experience',
    description:
      'An animated personal dashboard that presents profile, projects, GitHub activity, certifications, and contact actions in a recruiter-friendly interface.',
    role: 'React developer and UI designer',
    stack: ['React.js', 'Tailwind CSS', 'Framer Motion', 'GitHub API', 'EmailJS'],
    github: '',
    live: '',
    preview: 'dashboard',
    impact: ['Animated sections', 'GitHub signals', 'Responsive UI', 'Contact form'],
  },
]

export const experience = [
  {
    company: 'Robo Waves Technologies',
    role: 'Full Stack Developer Intern',
    period: 'Dec 2025 - Apr 2026',
    description:
      'Completed internship work focused on Java web development, Spring Boot, MVC workflows, REST APIs, and client-oriented application modules.',
    points: ['Built internship project modules', 'Worked across backend and UI layers', 'Practiced production-style debugging and delivery'],
  },
]

export const education = {
  college: 'SVCET',
  university: 'Savitribai Phule Pune University',
  degree: 'BE Computer Engineering',
  period: '2022 - 2026',
  cgpa: '8.1 CGPA',
  coursework: ['OOP', 'DBMS', 'Computer Networks', 'Operating Systems', 'Software Engineering', 'Web Technologies'],
}

export const certifications = [
  {
    name: 'TCS iON NQT Java Hands-On Assessment',
    meta: '90.80% score, December 2025',
  },
  {
    name: 'HackerRank Software Developer Certification',
    meta: 'Software development fundamentals and coding assessment',
  },
]

export const credibility = [
  'Completed full-stack internship work at Robo Waves Technologies',
  'Built Java/Spring Boot applications using layered MVC architecture',
  'Participated in WARTECH Frontend Hackathon by QSpiders Deccan',
  'Participated in Programming Hackathon by QSpiders Deccan',
  'Comfortable with GitHub-based project sharing and deployment workflows',
]
