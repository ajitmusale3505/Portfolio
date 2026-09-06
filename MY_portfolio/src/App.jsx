import { useEffect, useMemo, useRef, useState } from "react";
import emailjs from "@emailjs/browser";
import gsap from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import { motion } from "framer-motion";
import {
  ArrowUpRight,
  Award,
  BriefcaseBusiness,
  Check,
  Code2,
  Copy,
  Download,
  ExternalLink,
  FileText,
  GraduationCap,
  Lightbulb,
  Mail,
  MapPin,
  Menu,
  MessageCircle,
  Monitor,
  Phone,
  Play,
  Quote,
  Rocket,
  Send,
  ShieldCheck,
  Sparkles,
  Star,
  Users,
  X,
  ChevronLeft,
ChevronRight,
} from "lucide-react";
import HeroScene from "./components/HeroScene.jsx";
import {
  certifications,
  credibility,
  education,
  experience,
  metrics,
  profile,
  projects,
  skills,
  techLogos,
  training,
} from "./data/portfolio.js";

gsap.registerPlugin(ScrollTrigger);

const navItems = ["about", "skills", "projects", "experience", "contact"];

const stageTechStack = [
  {
    name: "Java",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg",
    tone: "#f89820",
  },
  {
    name: "Spring Boot",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg",
    tone: "#6db33f",
  },
  {
    name: "React",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg",
    tone: "#61dafb",
  },
  {
    name: "JavaScript",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg",
    tone: "#f7df1e",
  },
  {
    name: "TypeScript",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/typescript/typescript-original.svg",
    tone: "#3178c6",
  },
  {
    name: "PostgreSQL",
    src: "https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg",
    tone: "#4169e1",
  },
  { name: "REST API", icon: Code2, tone: "#a78bfa" },
];
const posterExtensions = [
  "png",
  "jpg",
  "jpeg",
  "webp",
  "avif",
  "gif",
  "svg",
  "jpj",
];

function GithubIcon({ size = 20 }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="currentColor"
      aria-hidden="true"
    >
      <path d="M12 .5C5.65.5.5 5.65.5 12c0 5.1 3.29 9.4 7.86 10.93.58.1.79-.25.79-.56v-2.02c-3.2.7-3.88-1.38-3.88-1.38-.53-1.34-1.3-1.7-1.3-1.7-1.06-.72.08-.7.08-.7 1.17.08 1.79 1.2 1.79 1.2 1.04 1.78 2.73 1.27 3.4.97.1-.75.4-1.27.73-1.56-2.56-.29-5.26-1.28-5.26-5.7 0-1.26.45-2.3 1.2-3.1-.12-.3-.52-1.48.11-3.08 0 0 .98-.31 3.2 1.18a11.1 11.1 0 0 1 5.82 0c2.22-1.49 3.2-1.18 3.2-1.18.63 1.6.23 2.78.11 3.08.75.8 1.2 1.84 1.2 3.1 0 4.43-2.7 5.41-5.27 5.7.42.36.79 1.07.79 2.16v3.03c0 .31.21.67.8.56A11.51 11.51 0 0 0 23.5 12C23.5 5.65 18.35.5 12 .5Z" />
    </svg>
  );
}

function LinkedinIcon({ size = 20 }) {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="currentColor"
      aria-hidden="true"
    >
      <path d="M20.45 20.45h-3.55v-5.57c0-1.33-.03-3.04-1.85-3.04-1.85 0-2.14 1.45-2.14 2.94v5.67H9.35V9h3.41v1.56h.05c.48-.9 1.64-1.85 3.37-1.85 3.6 0 4.27 2.37 4.27 5.46v6.28ZM5.34 7.43a2.06 2.06 0 1 1 0-4.13 2.06 2.06 0 0 1 0 4.13Zm1.78 13.02H3.56V9h3.56v11.45ZM22.23 0H1.77C.79 0 0 .77 0 1.73v20.54C0 23.23.79 24 1.77 24h20.45c.98 0 1.78-.77 1.78-1.73V1.73C24 .77 23.2 0 22.23 0Z" />
    </svg>
  );
}

function SectionHeading({ eyebrow, title, copy }) {
  return (
    <div className="section-heading reveal">
      <p>{eyebrow}</p>
      <h2>{title}</h2>
      {copy && <span>{copy}</span>}
    </div>
  );
}

function TechLogoCloud() {
  return (
    <div className="tech-stack-showcase" aria-label="Technology stack">
      <div className="tech-stack-head">
        <span className="tech-stack-kicker">
          <Sparkles size={13} />
          Core Stack
        </span>
        <span className="tech-stack-note">
          Built for modern full-stack development
        </span>
      </div>
      <div className="tech-stack-grid">
        {stageTechStack.map((tech) => {
          const Icon = tech.icon;
          return (
            <div
              className="tech-stack-item"
              style={{ "--tone": tech.tone }}
              key={tech.name}
            >
              <div className="tech-stack-icon">
                {tech.src ? (
                  <img src={tech.src} alt="" loading="lazy" />
                ) : (
                  <Icon size={31} strokeWidth={1.7} />
                )}
              </div>
              <span>{tech.name}</span>
            </div>
          );
        })}
      </div>
      <div className="tech-stack-progress" aria-hidden="true">
        <span />
      </div>
    </div>
  );
}

function ProfileOrbit() {
  return (
    <div className="profile-orbit" aria-hidden="true">
      {techLogos.slice(0, 4).map((logo, index) => (
        <span
          className={`orbit-chip orbit-${index + 1}`}
          style={{ "--tone": logo.tone }}
          key={logo.name}
        >
          <img src={logo.src} alt="" loading="lazy" />
        </span>
      ))}
      <i />
      <i />
    </div>
  );
}

function ProjectScreenshot({ type, name }) {
  const labels = {
    food: ["Orders", "Menu", "Kitchen", "Delivery"],
    banking: ["Accounts", "Transfer", "Ledger", "Security"],
    commerce: ["Catalog", "Cart", "Orders", "Admin"],
    dashboard: ["Profile", "Repos", "Skills", "Contact"],
  };

  return (
    <div className={`mock-screen ${type}`}>
      <div className="mock-browser">
        <span />
        <span />
        <span />
      </div>
      <div className="mock-sidebar">
        {(labels[type] || labels.dashboard).map((label) => (
          <b key={label}>{label}</b>
        ))}
      </div>
      <div className="mock-content">
        <div className="mock-title">{name}</div>
        <div className="mock-chart">
          <i />
          <i />
          <i />
          <i />
        </div>
        <div className="mock-rows">
          <span />
          <span />
          <span />
        </div>
      </div>
    </div>
  );
}

function ProjectMedia({ project }) {
  const videoRef = useRef(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [videoFailed, setVideoFailed] = useState(false);
  const [posterIndex, setPosterIndex] = useState(0);
  const hasVideo = project.video && !videoFailed;
  const posterCandidates = project.posterBase
    ? posterExtensions.map((extension) => `${project.posterBase}.${extension}`)
    : project.poster
      ? [project.poster]
      : [];
  const activePoster = posterCandidates[posterIndex];

  const syncVideoMode = (playing) => {
    setIsPlaying(playing);

    window.requestAnimationFrame(() => {
      const hasActiveVideo = Array.from(
        document.querySelectorAll(".project-video-frame video"),
      ).some((video) => !video.paused && !video.ended);

      if (playing || hasActiveVideo) {
        document.documentElement.dataset.videoPlaying = "true";
      } else {
        delete document.documentElement.dataset.videoPlaying;
      }
    });
  };

  const handlePlay = () => {
    document.querySelectorAll(".project-video-frame video").forEach((video) => {
      if (video !== videoRef.current && !video.paused) {
        video.pause();
      }
    });

    syncVideoMode(true);
  };

  const playFromPoster = async () => {
    try {
      await videoRef.current?.play();
    } catch {
      syncVideoMode(false);
      setVideoFailed(true);
    }
  };

  if (!hasVideo) {
    return <ProjectScreenshot type={project.preview} name={project.name} />;
  }

  return (
    <div
      className={`project-video-frame ${project.preview}`}
      data-playing={isPlaying}
    >
      <video
        ref={videoRef}
        src={project.video}
        poster={activePoster}
        controls
        muted
        playsInline
        preload="none"
        onPlay={handlePlay}
        onPause={() => syncVideoMode(false)}
        onEnded={() => syncVideoMode(false)}
        onError={() => {
          syncVideoMode(false);
          setVideoFailed(true);
        }}
        aria-label={`${project.name} project recording`}
      />
      {!isPlaying && (
        <button
          className="project-video-poster"
          type="button"
          onClick={playFromPoster}
          aria-label={`Play ${project.name} project recording`}
        >
          {activePoster ? (
            <img
              src={activePoster}
              alt=""
              loading="lazy"
              onError={() => setPosterIndex((current) => current + 1)}
            />
          ) : (
            <ProjectScreenshot type={project.preview} name={project.name} />
          )}
        </button>
      )}
      <div className="video-play-orb" aria-hidden="true">
        <Play size={28} fill="currentColor" />
      </div>
    </div>
  );
}

function CertificateActions({ certificate, label }) {
  if (!certificate) return null;

  return (
    <div className="certificate-actions">
      <a
        href={certificate}
        target="_blank"
        rel="noreferrer"
        aria-label={`View ${label}`}
      >
        <ExternalLink size={15} />
        View
      </a>
      <a href={certificate} download aria-label={`Download ${label}`}>
        <Download size={15} />
        Download
      </a>
    </div>
  );
}

function ContactRow({
  icon,
  label,
  value,
  action,
  href,
  onCopy,
  copyText,
  accent = "cyan",
}) {
  const Icon = icon;

  const content = (
    <>
      <div className={`contact-row-icon ${accent}`}>
        <Icon size={25} />
      </div>

      <div className="contact-row-content">
        <span>{label}</span>
        <strong>{value}</strong>
      </div>

      {action === "copy" && (
        <button
          type="button"
          className="contact-copy-button"
          onClick={onCopy}
          aria-label={`Copy ${label}`}
        >
          <Copy size={18} />
          <span>Copy</span>
        </button>
      )}

      {action === "open" && (
        <a
          href={href}
          target="_blank"
          rel="noreferrer"
          className="contact-open-button"
          aria-label={`Open ${label}`}
        >
          <ExternalLink size={18} />
          <span>Open</span>
        </a>
      )}
    </>
  );

  return <div className="premium-contact-row">{content}</div>;
}

function App() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [roleIndex, setRoleIndex] = useState(0);
  const [formState, setFormState] = useState("idle");
  const [copiedField, setCopiedField] = useState("");
  const projectsTrackRef = useRef(null);


  const scrollProjects = (direction) => {
  if (!projectsTrackRef.current) return;

  const card = projectsTrackRef.current.querySelector(".project-card");

  const scrollAmount = card
    ? card.offsetWidth + 24
    : 540;

  projectsTrackRef.current.scrollBy({
    left: direction * scrollAmount,
    behavior: "smooth",
  });
};

  useEffect(() => {
    const roleTimer = setInterval(() => {
      setRoleIndex((current) => (current + 1) % profile.roles.length);
    }, 2200);

    let pointerFrame = 0;
    let scrollFrame = 0;

    const updatePointer = (event) => {
      if (pointerFrame) return;

      const { clientX, clientY } = event;
      pointerFrame = window.requestAnimationFrame(() => {
        document.documentElement.style.setProperty("--mouse-x", `${clientX}px`);
        document.documentElement.style.setProperty("--mouse-y", `${clientY}px`);
        pointerFrame = 0;
      });
    };

    const updateScroll = () => {
      if (scrollFrame) return;

      scrollFrame = window.requestAnimationFrame(() => {
        const maxScroll =
          document.documentElement.scrollHeight - window.innerHeight;
        const progress = maxScroll > 0 ? window.scrollY / maxScroll : 0;
        document.documentElement.style.setProperty(
          "--scroll-progress",
          progress.toFixed(4),
        );
        document.documentElement.style.setProperty(
          "--scroll-y",
          `${window.scrollY}px`,
        );
        scrollFrame = 0;
      });
    };

    window.addEventListener("pointermove", updatePointer);
    window.addEventListener("scroll", updateScroll, { passive: true });
    updateScroll();

    const ctx = gsap.context(() => {
      gsap.utils.toArray(".reveal").forEach((el) => {
        gsap.fromTo(
          el,
          { y: 42, opacity: 0 },
          {
            y: 0,
            opacity: 1,
            duration: 0.85,
            ease: "power3.out",
            scrollTrigger: {
              trigger: el,
              start: "top 86%",
            },
          },
        );
      });
    });

    return () => {
      clearInterval(roleTimer);
      window.cancelAnimationFrame(pointerFrame);
      window.cancelAnimationFrame(scrollFrame);
      window.removeEventListener("pointermove", updatePointer);
      window.removeEventListener("scroll", updateScroll);
      ctx.revert();
    };
  }, []);

  const githubUser = useMemo(
    () => profile.github.split("/").filter(Boolean).pop(),
    [],
  );

  const sendEmail = async (event) => {
    event.preventDefault();
    setFormState("sending");

    const serviceId = import.meta.env.VITE_EMAILJS_SERVICE_ID;
    const templateId = import.meta.env.VITE_EMAILJS_TEMPLATE_ID;
    const publicKey = import.meta.env.VITE_EMAILJS_PUBLIC_KEY;

    if (!serviceId || !templateId || !publicKey) {
      setFormState("missing-config");
      return;
    }

    try {
      await emailjs.sendForm(serviceId, templateId, event.currentTarget, {
        publicKey,
      });
      event.currentTarget.reset();
      setFormState("sent");
    } catch {
      setFormState("error");
    }
  };

  const copyContact = async (text, field) => {
    try {
      await navigator.clipboard.writeText(text);

      setCopiedField(field);

      window.setTimeout(() => {
        setCopiedField("");
      }, 1800);
    } catch {
      // Clipboard permission may be unavailable in some browsers.
    }
  };

  return (
    <main>
      <div className="site-cursor-glow" aria-hidden="true" />
      <div className="scroll-energy" aria-hidden="true">
        <span />
      </div>
      <nav className="nav">
        <a href="#home" className="brand" aria-label="Ajit Musale home">
          <span>{profile.initials}</span>
          {profile.shortName}
        </a>
        <div className="nav-links">
          {navItems.map((item) => (
            <a key={item} href={`#${item}`}>
              {item}
            </a>
          ))}
        </div>
        <button
          className="icon-button menu-button"
          type="button"
          onClick={() => setMenuOpen(true)}
          aria-label="Open menu"
        >
          <Menu size={20} />
        </button>
      </nav>

      {menuOpen && (
        <div className="mobile-panel">
          <button
            className="icon-button"
            type="button"
            onClick={() => setMenuOpen(false)}
            aria-label="Close menu"
          >
            <X size={20} />
          </button>
          {navItems.map((item) => (
            <a key={item} href={`#${item}`} onClick={() => setMenuOpen(false)}>
              {item}
            </a>
          ))}
        </div>
      )}

      <section id="home" className="hero">
        <div className="hero-scene">
          <HeroScene />
        </div>
        <div className="hero-grid" />
        <div className="hero-content">
          <motion.div
            initial={{ opacity: 0, y: 24 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.75 }}
            className="hero-copy"
          >
            <div className="status-pill">
              <Sparkles size={16} />
              Available for Java, Spring Boot and full-stack roles
            </div>
            <h1>
              {profile.name}
              <span>{profile.roles[roleIndex]}</span>
            </h1>
            <p>{profile.bio}</p>
            <div className="hero-actions">
              <a href="#projects" className="primary-btn">
                <Rocket size={18} />
                View Projects
              </a>
              <a href={profile.resume} className="secondary-btn" download>
                <Download size={18} />
                Resume
              </a>
            </div>
            <div className="social-row">
              <a
                href={profile.github}
                target="_blank"
                rel="noreferrer"
                aria-label="GitHub"
              >
                <GithubIcon size={20} />
              </a>
              <a
                href={profile.linkedin}
                target="_blank"
                rel="noreferrer"
                aria-label="LinkedIn"
              >
                <LinkedinIcon size={20} />
              </a>
              <a
                href={`https://mail.google.com/mail/u/0/?view=cm&fs=1&to=${encodeURIComponent(profile.email)}`}
                aria-label="Email"
                onClick={(event) => {
                  event.preventDefault();
                  window.location.assign(
                    `https://mail.google.com/mail/u/0/?view=cm&fs=1&to=${encodeURIComponent(profile.email)}`,
                  );
                }}
              >
                <Mail size={20} />
              </a>
            </div>
            <TechLogoCloud />
          </motion.div>

          <motion.div
            initial={{ opacity: 0, scale: 0.94 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.8, delay: 0.2 }}
            className="profile-card"
          >
            <ProfileOrbit />
            <div className="profile-photo">
              <img src={profile.photo} alt="Ajit Musale" />
            </div>
            <div>
              <p className="mono">Based in</p>
              <h3>
                <MapPin size={18} />
                {profile.location}
              </h3>
            </div>
            <div className="mini-stack">
              <span>Spring Boot</span>
              <span>React.js</span>
              <span>REST APIs</span>
              <span>PostgreSQL</span>
            </div>
          </motion.div>
        </div>
      </section>

      <section className="metrics-band">
        {metrics.map((item) => (
          <div className="metric reveal" key={item.label}>
            <strong>{item.value}</strong>
            <span>{item.label}</span>
          </div>
        ))}
      </section>

      <section id="about" className="section about-section">
        <SectionHeading
          eyebrow="About"
          title="Backend-minded full-stack developer with real internship delivery."
          copy="The portfolio is shaped for recruiters and engineering managers: clear proof, strong projects, and professional motion."
        />
        <div className="about-layout">
          <div className="glass-panel reveal">
            <Code2 size={28} />
            <h3>What I build</h3>
            <p>
              I focus on Java web applications with clean MVC structure, REST
              API layers, database-backed features, and React interfaces that
              make backend work easy to see and test.
            </p>
          </div>
          <div className="glass-panel reveal">
            <BriefcaseBusiness size={28} />
            <h3>Internship experience</h3>
            <p>
              At Robo Waves Technologies, I worked across Spring Boot, MVC,
              Servlets, JSP, and REST flows while building project modules with
              practical delivery expectations.
            </p>
          </div>
          <div className="glass-panel reveal">
            <ShieldCheck size={28} />
            <h3>Current target</h3>
            <p>
              Java Developer, Spring Boot Developer, Backend Developer, and Full
              Stack Developer roles where strong fundamentals and project
              ownership matter.
            </p>
          </div>
        </div>
      </section>

      <section id="skills" className="section">
        <SectionHeading
          eyebrow="Skills"
          title="A practical stack for Java full-stack work."
        />
        <div className="skills-grid">
          {skills.map((group) => (
            <div className="skill-card reveal" key={group.group}>
              <div className="skill-head">
                <div className="skill-logo">
                  <img src={group.icon} alt="" loading="lazy" />
                </div>
                <div>
                  <h3>{group.group}</h3>
                  <p>{group.summary}</p>
                </div>
              </div>
              <div
                className="skill-meter"
                aria-label={`${group.group} skill strength ${group.level}%`}
              >
                <span style={{ width: `${group.level}%` }} />
              </div>
              <div className="skill-tags">
                {group.items.map((skill) => (
                  <span key={skill}>{skill}</span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </section>

      <section id="projects" className="section projects-section">
  <div className="projects-heading-row reveal">
    <SectionHeading
      eyebrow="Projects"
      title="Selected Work & Case Studies."
      copy="A curated collection of full-stack projects, interactive demos, project recordings, and production-focused features."
    />

    <div className="projects-navigation">
      <button
        type="button"
        className="project-nav-button"
        onClick={() => scrollProjects(-1)}
        aria-label="Previous project"
      >
        <ChevronLeft size={20} />
      </button>

      <button
        type="button"
        className="project-nav-button"
        onClick={() => scrollProjects(1)}
        aria-label="Next project"
      >
        <ChevronRight size={20} />
      </button>
    </div>
  </div>

  <div className="projects-showcase-wrapper">
    <div
      className="projects-grid projects-horizontal-scroll"
      ref={projectsTrackRef}
    >
      {projects.map((project, index) => (
        <article className="project-card reveal" key={project.name}>
          
          {/* PROJECT MEDIA */}
          <div className="project-visual">
            <div className="project-topbar">
              <span className="project-number">
                {String(index + 1).padStart(2, "0")}
              </span>

              <span className="featured-pill">
                <Star size={14} />
                Featured Project
              </span>
            </div>

            <ProjectMedia project={project} />
          </div>

          {/* PROJECT CONTENT */}
          <div className="project-body">

            <div className="project-category-row">
              <p className="mono">{project.type}</p>

              <span className="project-status">
                Available
              </span>
            </div>

            <h3>{project.name}</h3>

            <p className="project-description">
              {project.description}
            </p>

            {/* TECH STACK */}
            <div className="project-tech-section">
              <span className="project-mini-label">
                TECHNOLOGIES
              </span>

              <div className="tags">
                {project.stack.map((tech) => (
                  <span key={tech}>{tech}</span>
                ))}
              </div>
            </div>

            {/* FEATURES */}
            <div className="project-features">
              <span className="project-mini-label">
                KEY FEATURES
              </span>

              <ul>
                {project.impact.slice(0, 3).map((point) => (
                  <li key={point}>
                    <span className="feature-dot" />
                    {point}
                  </li>
                ))}
              </ul>
            </div>

            {/* ACTION BUTTONS */}
            <div className="project-links">
              <a
                className="live-link"
                href={project.live}
                target="_blank"
                rel="noreferrer"
              >
                <Monitor size={17} />
                Live Preview
                <ArrowUpRight size={16} />
              </a>

              {project.github ? (
                <a
                  className="github-project-link"
                  href={project.github}
                  target="_blank"
                  rel="noreferrer"
                >
                  GitHub
                  <ArrowUpRight size={16} />
                </a>
              ) : (
                <span className="project-coming-soon">
                  Repository coming soon
                </span>
              )}
            </div>

          </div>

          {/* DECORATIVE CORNER */}
          <div
            className="project-card-glow"
            aria-hidden="true"
          />

        </article>
      ))}
    </div>
  </div>

  <div className="projects-scroll-hint reveal">
    <span className="scroll-hint-line" />
    <span>Scroll horizontally to explore more projects</span>
  </div>
</section>  

      <section id="experience" className="section premium-journey-section">
        <div className="journey-header reveal">
          <SectionHeading
            eyebrow="Experience"
            title={
              <>
                My Professional <em>Journey</em>
              </>
            }
            copy="A focused timeline of my education, professional training, internship delivery and certifications."
          />
          <div className="experience-stat">
            <div className="experience-stat-icon">
              <BriefcaseBusiness size={22} />
            </div>
            <div>
              <strong>0.6</strong>
              <span>Years of experience</span>
            </div>
          </div>
        </div>

        <div className="premium-timeline">
          <div className="premium-timeline-line" aria-hidden="true" />

          <article className="journey-item reveal journey-education">
            <div className="journey-date">
              <strong>2022 – 2026</strong>
              <span>4 Years</span>
            </div>
            <div className="journey-node" aria-hidden="true">
              <GraduationCap size={20} />
            </div>
            <div className="journey-card">
              <div className="journey-card-main">
                <div className="journey-icon">
                  <GraduationCap size={24} />
                </div>
                <div>
                  <div className="journey-title-row">
                    <h3>{education.degree}</h3>
                    <span className="journey-badge">Education</span>
                  </div>
                  <strong>
                    {education.college}, {education.university}
                  </strong>
                  <p>
                    {education.cgpa}. Coursework includes{" "}
                    {education.coursework.join(", ")}.
                  </p>
                </div>
              </div>
              <div className="journey-side">
                <div className="journey-tags">
                  {education.coursework.map((item) => (
                    <span key={item}>{item}</span>
                  ))}
                </div>
                <CertificateActions
                  certificate={education.certificate}
                  label="BE Certificate"
                />
              </div>
            </div>
          </article>

          <article className="journey-item reveal journey-training">
            <div className="journey-date">
              <strong>AUG 2025 – JUN 2026</strong>
              <span>10 Months</span>
            </div>
            <div className="journey-node" aria-hidden="true">
              <Code2 size={19} />
            </div>
            <div className="journey-card">
              <div className="journey-card-main">
                <div className="journey-icon">
                  <Code2 size={24} />
                </div>
                <div>
                  <div className="journey-title-row">
                    <h3>{training.title}</h3>
                    <span className="journey-badge">Professional Training</span>
                  </div>
                  <strong>{training.institute}</strong>
                  <p>{training.description}</p>
                </div>
              </div>
              <div className="journey-side">
                <div className="journey-tags">
                  {training.skills.map((item) => (
                    <span key={item}>{item}</span>
                  ))}
                </div>
                <CertificateActions
                  certificate={training.certificate}
                  label="Training Certificate"
                />
              </div>
            </div>
          </article>

          {experience.map((item) => (
            <article
              className="journey-item reveal journey-internship"
              key={item.company}
            >
              <div className="journey-date">
                <strong>DEC 2025 – APR 2026</strong>
                <span>4 Months</span>
              </div>
              <div className="journey-node" aria-hidden="true">
                <BriefcaseBusiness size={19} />
              </div>
              <div className="journey-card">
                <div className="journey-card-main">
                  <div className="journey-icon">
                    <BriefcaseBusiness size={24} />
                  </div>
                  <div>
                    <div className="journey-title-row">
                      <h3>{item.role}</h3>
                      <span className="journey-badge">Internship</span>
                    </div>
                    <strong>{item.company}</strong>
                    <p>{item.description}</p>
                  </div>
                </div>
                <div className="journey-side">
                  <div className="journey-tags">
                    {[
                      "Java",
                      "Spring Boot",
                      "MVC",
                      "REST APIs",
                      "React.js",
                      "PostgreSQL",
                    ].map((tech) => (
                      <span key={tech}>{tech}</span>
                    ))}
                  </div>
                  <CertificateActions
                    certificate={item.certificate}
                    label="Internship Certificate"
                  />
                </div>
              </div>
            </article>
          ))}

          {certifications
            .filter((cert) => !cert.name.toLowerCase().includes("hackerrank"))
            .map((cert) => (
              <article
                className="journey-item reveal journey-certification"
                key={cert.name}
              >
                <div className="journey-date">
                  <strong>DEC 2025</strong>
                  <span>Certification</span>
                </div>
                <div className="journey-node" aria-hidden="true">
                  <Award size={19} />
                </div>
                <div className="journey-card certification-card">
                  <div className="journey-card-main">
                    <div className="journey-icon">
                      <Award size={24} />
                    </div>
                    <div>
                      <div className="journey-title-row">
                        <h3>{cert.name}</h3>
                        <span className="journey-badge">Certification</span>
                      </div>
                      <p>{cert.meta}</p>
                    </div>
                  </div>
                  <div className="journey-score">
                    <div className="score-ring">
                      <span>90.80%</span>
                    </div>
                    <small>Score</small>
                  </div>
                  <CertificateActions
                    certificate={cert.certificate}
                    label="TCS Certificate"
                  />
                </div>
              </article>
            ))}
        </div>
      </section>

      <section className="section credibility-section">
        <SectionHeading eyebrow="Credibility" title="What I have worked on." />
        <div className="cred-grid">
          {credibility.map((item) => (
            <div className="cred-card reveal" key={item}>
              <ShieldCheck size={18} />
              <span>{item}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="section github-section">
        <SectionHeading
          eyebrow="GitHub"
          title="Public coding profile."
          copy="The embedded card links directly to your GitHub. We can upgrade this to live API stats after deployment."
        />
        <a
          className="github-card reveal"
          href={profile.github}
          target="_blank"
          rel="noreferrer"
        >
          <GithubIcon size={34} />
          <div>
            <p className="mono">@{githubUser}</p>
            <h3>Explore repositories, commits, and Java/Spring Boot work.</h3>
          </div>
          <ArrowUpRight size={22} />
        </a>
      </section>

      <section id="contact" className="section premium-contact-section">
        <div className="contact-background-grid" aria-hidden="true" />
        <div className="contact-orb contact-orb-one" aria-hidden="true" />
        <div className="contact-orb contact-orb-two" aria-hidden="true" />

        <div className="premium-contact-container">
          {/* =====================================================
        LEFT SIDE
    ===================================================== */}

          <div className="premium-contact-intro reveal">
            <div className="premium-contact-label">
              <Send size={18} />
              <span>CONTACT</span>
            </div>

            <h2>
              Let's Build
              <br />
              Something
              <span>Amazing Together</span>
            </h2>

            <p>
              I'm always excited to discuss new opportunities, interesting
              projects, or just chat about technology. Feel free to reach out
              through any of the channels below. I'll get back to you as soon as
              possible!
            </p>

            {/* CONTACT HIGHLIGHTS */}

            <div className="contact-highlights">
              <div className="contact-highlight-item">
                <div className="highlight-icon highlight-cyan">
                  <MessageCircle size={25} />
                </div>

                <div>
                  <strong>Quick</strong>
                  <span>Response</span>
                </div>
              </div>

              <div className="contact-highlight-item">
                <div className="highlight-icon highlight-violet">
                  <Users size={25} />
                </div>

                <div>
                  <strong>Open to</strong>
                  <span>Opportunities</span>
                </div>
              </div>

              <div className="contact-highlight-item">
                <div className="highlight-icon highlight-gold">
                  <Lightbulb size={25} />
                </div>

                <div>
                  <strong>Let's</strong>
                  <span>Collaborate</span>
                </div>
              </div>
            </div>

            {/* QUOTE CARD */}

            <div className="contact-quote-card">
              <Quote size={30} />

              <div>
                <p>
                  Great ideas start with
                  <br />
                  <strong>a simple conversation.</strong>
                </p>
              </div>

              <div className="contact-paper-plane" aria-hidden="true">
                <Send size={44} />
              </div>
            </div>
          </div>

          {/* =====================================================
        RIGHT SIDE CONTACT PANEL
    ===================================================== */}

          <div className="premium-contact-panel reveal">
            <div className="contact-panel-header">
              <div>
                <div className="contact-panel-title">
                  <span className="contact-title-dot" />

                  <div>
                    <h3>Contact Information</h3>

                    <p>
                      Feel free to reach out through any of the channels below
                    </p>
                  </div>
                </div>
              </div>

              <div className="contact-availability-badge">
                <span />
                Available for opportunities
              </div>
            </div>

            {/* =====================================================
          CONTACT INFORMATION ROWS
      ===================================================== */}

            <div className="premium-contact-list">
              {/* EMAIL */}

              <ContactRow
                icon={Mail}
                label="Email"
                value={profile.email}
                action="copy"
                accent="blue"
                onCopy={() => copyContact(profile.email, "email")}
              />

              {/* PHONE */}

              {/* <ContactRow
                icon={Phone}
                label="Phone"
                value={profile.phone || "Add your phone number"}
                action="copy"
                accent="green"
                onCopy={() => copyContact(profile.phone || "", "phone")}
              /> */}

              {/* LOCATION */}

              <ContactRow
                icon={MapPin}
                label="Location"
                value={profile.location}
                action="copy"
                accent="violet"
                onCopy={() => copyContact(profile.location, "location")}
              />

              {/* LINKEDIN */}

              <ContactRow
                icon={LinkedinIcon}
                label="LinkedIn"
                value="linkedin.com/in/musale-ajit"
                action="open"
                href={profile.linkedin}
                accent="linkedin"
              />

              {/* GITHUB */}

              <ContactRow
                icon={GithubIcon}
                label="GitHub"
                value={`github.com/${githubUser}`}
                action="open"
                href={profile.github}
                accent="github"
              />

              {/* =====================================================
            WHATSAPP
        ===================================================== */}

              <a
                href={profile.whatsapp || "#"}
                target="_blank"
                rel="noreferrer"
                className={`premium-whatsapp-row ${
                  !profile.whatsapp ? "whatsapp-disabled" : ""
                }`}
                onClick={(event) => {
                  if (!profile.whatsapp) {
                    event.preventDefault();
                  }
                }}
              >
                <div className="whatsapp-glow" />

                <div className="whatsapp-row-icon">
                  <MessageCircle size={30} />
                </div>

                <div className="whatsapp-row-content">
                  <span>WhatsApp</span>

                  <strong>Let's chat on WhatsApp</strong>
                </div>

                <div className="whatsapp-chat-button">
                  <span className="whatsapp-pulse-ring" />

                  <MessageCircle size={23} />

                  <strong>Chat Now</strong>

                  <ArrowUpRight size={20} />
                </div>
              </a>
            </div>
          </div>

          {/* =====================================================
        SOCIAL SECTION
    ===================================================== */}

          <div className="premium-contact-social reveal">
            <div className="social-divider">
              <span />
              <p>FIND ME ON</p>
              <span />
            </div>

            <div className="premium-social-links">
              <a
                href={profile.github}
                target="_blank"
                rel="noreferrer"
                aria-label="GitHub"
              >
                <GithubIcon size={25} />
              </a>

              <a
                href={profile.linkedin}
                target="_blank"
                rel="noreferrer"
                aria-label="LinkedIn"
              >
                <LinkedinIcon size={24} />
              </a>

              <a
                href={`https://mail.google.com/mail/u/0/?view=cm&fs=1&to=${encodeURIComponent(
                  profile.email,
                )}`}
                aria-label="Send email"
              >
                <Mail size={23} />
              </a>

              <a
                href={profile.whatsapp || "#"}
                target="_blank"
                rel="noreferrer"
                aria-label="WhatsApp"
                onClick={(event) => {
                  if (!profile.whatsapp) {
                    event.preventDefault();
                  }
                }}
              >
                <MessageCircle size={23} />
              </a>
            </div>

            <p className="contact-closing-note">
              Looking forward
              <br />
              to hearing from you!
            </p>
          </div>
        </div>
      </section>

      <footer>
        <span>{profile.initials}</span>
        <p>Built with React, JSX, Three.js, GSAP, Framer Motion and EmailJS.</p>
      </footer>
    </main>
  );
}

export default App;
