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
  whatsapp: 'PASTE_YOUR_WHATSAPP_LINK_HERE',

  roles: [
    'Java Developer',
    'Spring Boot Developer',
    'MERN Developer',
    'Full Stack Developer',
    'Backend Developer',
  ],

  bio:
    'Computer Engineering graduate with hands-on experience in Java Full Stack development at Robo Wave Technologies. I build practical web applications using Java, Spring Boot, REST APIs, React.js, and PostgreSQL, with a strong focus on clean backend logic and reliable database-driven workflows. I enjoy turning ideas into working software and solving problems that go beyond just writing code.',
}


export const metrics = [
  {
    value: '8.38',
    label: 'CGPA',
  },
  {
    value: '90.80%',
    label: 'TCS iON Java Hands-On',
  },
  {
    value: '4+',
    label: 'Production-style Projects',
  },
  {
    value: '2',
    label: 'Hackathons',
  },
]


export const techLogos = [
  {
    name: 'Java',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
    tone: '#f89820',
  },
  {
    name: 'Spring',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
    tone: '#6db33f',
  },
  {
    name: 'React',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg',
    tone: '#61dafb',
  },
  {
    name: 'PostgreSQL',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg',
    tone: '#336791',
  },
  {
    name: 'JavaScript',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg',
    tone: '#f7df1e',
  },
  {
    name: 'Tailwind',
    src: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/tailwindcss/tailwindcss-original.svg',
    tone: '#38bdf8',
  },
]


export const skills = [
  {
    group: 'Frontend',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg',
    level: 82,
    summary:
      'Interfaces, state-driven UI, responsive layouts and API integration.',
    items: [
      'HTML',
      'CSS',
      'JavaScript',
      'React.js',
      'Tailwind CSS',
      'JSON',
      'XML',
    ],
  },

  {
    group: 'Backend',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg',
    level: 88,
    summary:
      'Spring Boot, MVC patterns, REST APIs, service layers and server-side workflows.',
    items: [
      'Java',
      'Spring Boot',
      'Spring MVC',
      'Hibernate/JPA',
      'Servlets',
      'REST APIs',
    ],
  },

  {
    group: 'Database',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg',
    level: 78,
    summary:
      'SQL schema design, CRUD operations, JDBC and relational data modeling.',
    items: [
      'PostgreSQL',
      'MySQL',
      'JDBC',
      'Relational Design',
      'CRUD Workflows',
    ],
  },

  {
    group: 'Tools',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg',
    level: 74,
    summary:
      'Daily development workflow with IDEs, Maven, GitHub and deployment tooling.',
    items: [
      'STS',
      'Maven',
      'Eclipse',
      'VS Code',
      'Git',
      'GitHub',
      'Vercel',
    ],
  },

  {
    group: 'Core Concepts',
    icon: 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg',
    level: 80,
    summary:
      'OOP, debugging, layered architecture, authentication flow and problem solving.',
    items: [
      'OOP',
      'MVC Architecture',
      'Authentication Flow',
      'API Integration',
      'Debugging',
    ],
  },
]


export const projects = [
  {
    name: 'Food Delivery Management System',
    type: 'Internship Project',
    description:
      'A full-stack food ordering workflow built with Spring Boot and MVC patterns, covering restaurant listings, menu management, cart flow, order placement, and admin-side operations.',
    role: 'Solo developer during Robo Waves Technologies internship',
    stack: [
      'Spring Boot',
      'Spring MVC',
      'REST API',
      'JSP',
      'Servlets',
      'PostgreSQL',
    ],
    github: 'https://github.com/ajitmusale3505/QuickBite',
    live: 'https://example.com/food-delivery-management-system',
    video:
      '/assets/project-videos/food-delivery-management-system.mp4',
    posterBase:
      '/assets/project-posters/food-delivery-management-system',
    preview: 'food',
    impact: [
      'Role-based flows',
      'Order lifecycle handling',
      'Structured MVC layers',
      'Database-backed CRUD',
    ],
  },

  {
    name: 'Internet Banking System',
    type: 'Secure Backend Workflow',
    description:
      'A banking application concept focused on account management, fund transfers, transaction history, authentication, and admin controls using a Java web stack.',
    role: 'Full-stack implementation and database workflow design',
    stack: [
      'Spring Boot',
      'Spring MVC',
      'REST API',
      'JSP',
      'Servlets',
      'JDBC',
      'PostgreSQL',
    ],
    github: 'https://github.com/ajitmusale3505/internet-banking',
    live: 'https://example.com/internet-banking-system',
    video:
      '/assets/project-videos/internet-banking-system.mp4',
    posterBase:
      '/assets/project-posters/internet-banking-system',
    preview: 'banking',
    impact: [
      'Account dashboard',
      'Transaction records',
      'Authentication flow',
      'Admin monitoring',
    ],
  },

  {
    name: 'E-Commerce Platform',
    type: 'Full Stack Application',
    description:
      'A commerce platform architecture with product catalog, cart, checkout-ready order flow, authentication, and admin product management.',
    role: 'Full-stack developer',
    stack: [
      'React.js',
      'Spring Boot',
      'REST API',
      'PostgreSQL',
      'Tailwind CSS',
    ],
    github: '',
    live: 'https://example.com/e-commerce-platform',
    video:
      '/assets/project-videos/e-commerce-platform.mp4',
    posterBase:
      '/assets/project-posters/e-commerce-platform',
    preview: 'commerce',
    impact: [
      'Product catalog',
      'Cart flow',
      'Admin inventory',
      'API-driven UI',
    ],
  },

  {
    name: 'Developer Portfolio Dashboard',
    type: 'Frontend + API Experience',
    description:
      'An animated personal dashboard that presents profile, projects, GitHub activity, certifications, and contact actions in a recruiter-friendly interface.',
    role: 'React developer and UI designer',
    stack: [
      'React.js',
      'Tailwind CSS',
      'Framer Motion',
      'GitHub API',
      'EmailJS',
    ],
    github: '',
    live: 'https://example.com/developer-portfolio-dashboard',
    video:
      '/assets/project-videos/developer-portfolio-dashboard.mp4',
    posterBase:
      '/assets/project-posters/developer-portfolio-dashboard',
    preview: 'dashboard',
    impact: [
      'Animated sections',
      'GitHub signals',
      'Responsive UI',
      'Contact form',
    ],
  },
]


/* ==========================================================================
   PROFESSIONAL EXPERIENCE
   ========================================================================== */

export const experience = [
  {
    company: 'Robo Waves Technologies',

    role: 'Full Stack Developer Intern',

    period: 'Dec 2025 - Apr 2026',

    duration: '4 Months',

    type: 'Internship',

    description:
      'Worked on Java web development, Spring Boot, MVC workflows, REST APIs, React.js and client-oriented application modules while contributing to practical project development and delivery.',

    points: [
      'Built and enhanced internship project modules',
      'Worked across backend and frontend layers',
      'Implemented REST API and MVC workflows',
      'Worked with PostgreSQL database operations',
      'Practiced production-style debugging and delivery',
    ],

    skills: [
      'Java',
      'Spring Boot',
      'MVC',
      'REST APIs',
      'React.js',
      'PostgreSQL',
    ],

    certificate:
      '/assets/certificates/robo-waves-internship-certificate.pdf',
  },
]


/* ==========================================================================
   EDUCATION
   ========================================================================== */

export const education = {
  college: 'SVCET',

  university: 'Savitribai Phule Pune University',

  degree: 'BE Computer Engineering',

  period: '2022 - 2026',

  duration: '4 Years',

  type: 'Education',

  cgpa: '8.38 CGPA',

  description:
    'Completed Bachelor of Engineering in Computer Engineering with coursework covering object-oriented programming, database management, computer networks, operating systems, software engineering and web technologies.',

  coursework: [
    'OOP',
    'DBMS',
    'Computer Networks',
    'Operating Systems',
    'Software Engineering',
    'Web Technologies',
    'DSA',
  ],

  certificate:
    '/assets/certificates/be-certificate.pdf',
}


/* ==========================================================================
   PROFESSIONAL TRAINING
   ========================================================================== */

export const training = {
  title: 'QSpiders Full Stack Java Development Course',

  institute: 'QSpiders Deccan, Pune',

  period: 'Aug 2025 - Jun 2026',

  duration: '10 Months',

  type: 'Professional Training',

  description:
    'Comprehensive training in Java full-stack development including Core Java, Spring Boot, REST APIs, React.js, SQL and real-time project development.',

  skills: [
    'Java',
    'Spring Boot',
    'REST APIs',
    'React.js',
    'SQL',
    'HTML',
    'CSS',
  ],

  certificate:
    '/assets/certificates/qspiders-full-stack-certificate.pdf',
}


/* ==========================================================================
   CERTIFICATIONS
   ========================================================================== */

export const certifications = [
  {
    name: 'TCS iON NQT Java Hands-On Assessment',

    meta: '90.80% score, December 2025',

    period: 'Dec 2025',

    duration: 'Certification',

    type: 'Certification',

    score: '90.80%',

    description:
      'Achieved a 90.80% score in the TCS iON NQT Java Hands-On Assessment.',

    certificate:
      '/assets/certificates/tcs-ion-nqt-java-certificate.pdf',
  },
]


/* ==========================================================================
   PROFESSIONAL JOURNEY METADATA
   ========================================================================== */

export const journey = {
  yearsOfExperience: '0.6+',

  experienceLabel: 'Years of experience',

  title: 'My Professional Journey',

  subtitle:
    'A timeline of my education, training, internship and certification milestones that built my skills and shaped my career path.',
}


/* ==========================================================================
   CREDIBILITY
   ========================================================================== */

export const credibility = [
  'Completed full-stack internship work at Robo Waves Technologies',

  'Built Java and Spring Boot applications using layered MVC architecture',

  'Completed Full Stack Java Development training at QSpiders Deccan',

  'Achieved 90.80% in TCS iON NQT Java Hands-On Assessment',

  'Participated in WARTECH Frontend Hackathon by QSpiders Deccan',

  'Participated in Programming Hackathon by QSpiders Deccan',

  'Comfortable with GitHub-based project sharing and deployment workflows',
]