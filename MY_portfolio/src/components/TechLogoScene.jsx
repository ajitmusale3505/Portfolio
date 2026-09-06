import { Canvas, useFrame } from '@react-three/fiber'
import { Float, RoundedBox, Text } from '@react-three/drei'
import { Suspense, useRef, useState } from 'react'

const badgeMaterial = {
  metalness: 0.68,
  roughness: 0.18,
  envMapIntensity: 1.4,
}

function RaisedBadge({ children, label, color, accent, position, rotation = [0, 0, 0] }) {
  const group = useRef()
  const frame = useRef(0)
  const [hovered, setHovered] = useState(false)

  useFrame((state) => {
    frame.current += 1
    if (frame.current % 2 !== 0 || !group.current) return
    const t = state.clock.elapsedTime
    group.current.rotation.y = rotation[1] + Math.sin(t * 0.55 + position[0]) * 0.12 + state.pointer.x * 0.16
    group.current.rotation.x = rotation[0] + Math.cos(t * 0.5 + position[1]) * 0.08 - state.pointer.y * 0.12
    group.current.scale.setScalar(hovered ? 1.12 : 1)
  })

  return (
    <Float speed={1.65} rotationIntensity={0.1} floatIntensity={0.26}>
      <group
        ref={group}
        position={position}
        rotation={rotation}
        onPointerOver={() => setHovered(true)}
        onPointerOut={() => setHovered(false)}
      >
        <RoundedBox args={[1.24, 1.24, 0.24]} radius={0.18} smoothness={5}>
          <meshPhysicalMaterial
            color="#111827"
            emissive={accent}
            emissiveIntensity={hovered ? 0.16 : 0.06}
            clearcoat={1}
            clearcoatRoughness={0.12}
            {...badgeMaterial}
          />
        </RoundedBox>
        <RoundedBox args={[1.02, 1.02, 0.08]} radius={0.15} smoothness={5} position={[0, 0, 0.16]}>
          <meshPhysicalMaterial
            color={color}
            emissive={accent}
            emissiveIntensity={hovered ? 0.58 : 0.26}
            clearcoat={1}
            clearcoatRoughness={0.08}
            metalness={0.35}
            roughness={0.16}
          />
        </RoundedBox>
        <mesh position={[0, 0, 0.225]}>
          <ringGeometry args={[0.46, 0.5, 72]} />
          <meshStandardMaterial color="#ffffff" transparent opacity={0.32} emissive="#ffffff" emissiveIntensity={0.18} />
        </mesh>
        <group position={[0, 0.05, 0.3]}>{children}</group>
        <Text position={[0, -0.94, 0.1]} fontSize={0.16} anchorX="center" color="#eef6ff" outlineWidth={0.008} outlineColor="#020617">
          {label}
        </Text>
      </group>
    </Float>
  )
}

function ReactMark() {
  return (
    <group scale={0.88}>
      {[0, Math.PI / 3, -Math.PI / 3].map((rotation) => (
        <mesh key={rotation} rotation={[Math.PI / 2, 0, rotation]}>
          <torusGeometry args={[0.33, 0.025, 10, 48]} />
          <meshPhysicalMaterial color="#dffbff" emissive="#61dafb" emissiveIntensity={0.72} metalness={0.35} roughness={0.12} clearcoat={1} />
        </mesh>
      ))}
      <mesh>
        <sphereGeometry args={[0.09, 16, 16]} />
        <meshPhysicalMaterial color="#ffffff" emissive="#61dafb" emissiveIntensity={1} metalness={0.2} roughness={0.1} />
      </mesh>
    </group>
  )
}

function JavaMark() {
  return (
    <group scale={0.82}>
      <mesh position={[0, -0.08, 0]} rotation={[0, 0, 0]}>
        <cylinderGeometry args={[0.25, 0.31, 0.32, 32]} />
        <meshPhysicalMaterial color="#fff7ed" emissive="#f97316" emissiveIntensity={0.24} metalness={0.28} roughness={0.12} clearcoat={1} />
      </mesh>
      <mesh position={[0.34, -0.08, 0]} rotation={[Math.PI / 2, 0, 0]}>
        <torusGeometry args={[0.14, 0.027, 16, 54, Math.PI * 1.4]} />
        <meshPhysicalMaterial color="#ffffff" emissive="#fb923c" emissiveIntensity={0.24} />
      </mesh>
      {[0, 1, 2].map((i) => (
        <mesh key={i} position={[-0.16 + i * 0.14, 0.23 + i * 0.07, 0.02]} rotation={[0, 0, 0.42]}>
          <torusGeometry args={[0.13, 0.012, 10, 42, Math.PI]} />
          <meshStandardMaterial color="#ef4444" emissive="#ef4444" emissiveIntensity={0.8} />
        </mesh>
      ))}
    </group>
  )
}

function SpringMark() {
  return (
    <group scale={0.9} rotation={[0, 0, -0.22]}>
      <mesh scale={[0.56, 0.34, 0.1]}>
        <sphereGeometry args={[0.55, 24, 14]} />
        <meshPhysicalMaterial color="#ecfccb" emissive="#a3e635" emissiveIntensity={0.32} metalness={0.18} roughness={0.12} clearcoat={1} />
      </mesh>
      <mesh position={[0.08, 0.03, 0.09]} rotation={[0, 0, -0.72]}>
        <torusGeometry args={[0.27, 0.018, 8, 36, Math.PI * 1.2]} />
        <meshStandardMaterial color="#166534" emissive="#22c55e" emissiveIntensity={0.4} />
      </mesh>
      <mesh position={[0.22, -0.12, 0.05]} rotation={[0, 0, -0.62]}>
        <boxGeometry args={[0.34, 0.035, 0.035]} />
        <meshStandardMaterial color="#166534" emissive="#22c55e" emissiveIntensity={0.28} />
      </mesh>
    </group>
  )
}

function DatabaseMark() {
  return (
    <group scale={0.74}>
      {[
        [0, 0.25, '#dbeafe'],
        [0, 0, '#60a5fa'],
        [0, -0.25, '#2563eb'],
      ].map(([x, y, color], index) => (
        <mesh key={index} position={[x, y, 0]}>
          <cylinderGeometry args={[0.38, 0.38, 0.18, 32]} />
          <meshPhysicalMaterial color={color} emissive="#38bdf8" emissiveIntensity={0.18} metalness={0.4} roughness={0.16} clearcoat={1} />
        </mesh>
      ))}
    </group>
  )
}

function JsMark() {
  return (
    <group>
      <RoundedBox args={[0.68, 0.68, 0.18]} radius={0.08} smoothness={6}>
        <meshPhysicalMaterial color="#fef08a" emissive="#facc15" emissiveIntensity={0.38} metalness={0.22} roughness={0.12} clearcoat={1} />
      </RoundedBox>
      <Text position={[0, -0.02, 0.13]} fontSize={0.28} fontWeight={800} anchorX="center" anchorY="middle" color="#171717">
        JS
      </Text>
    </group>
  )
}

function ApiMark() {
  return (
    <group>
      <RoundedBox args={[0.72, 0.46, 0.18]} radius={0.07} smoothness={6}>
        <meshPhysicalMaterial color="#ede9fe" emissive="#8b5cf6" emissiveIntensity={0.3} metalness={0.28} roughness={0.12} clearcoat={1} />
      </RoundedBox>
      {[0, 1, 2].map((i) => (
        <mesh key={i} position={[-0.22 + i * 0.22, -0.13, 0.12]}>
          <sphereGeometry args={[0.035, 10, 10]} />
          <meshStandardMaterial color="#6d28d9" emissive="#7c3aed" emissiveIntensity={0.5} />
        </mesh>
      ))}
      <Text position={[0, 0.05, 0.13]} fontSize={0.17} anchorX="center" anchorY="middle" color="#2e1065">
        API
      </Text>
    </group>
  )
}

function LogoRig() {
  const rig = useRef()
  const frame = useRef(0)

  useFrame((state) => {
    frame.current += 1
    if (frame.current % 2 !== 0 || !rig.current) return
    rig.current.rotation.y = state.pointer.x * 0.12
    rig.current.rotation.x = -state.pointer.y * 0.06
  })

  return (
    <group ref={rig}>
      <RaisedBadge label="Java" color="#f97316" accent="#fb923c" position={[-2.72, 0.72, 0]} rotation={[0.08, 0.28, -0.05]}>
        <JavaMark />
      </RaisedBadge>
      <RaisedBadge label="Spring" color="#22c55e" accent="#84cc16" position={[-1.62, -0.7, 0.25]} rotation={[-0.04, -0.18, 0.05]}>
        <SpringMark />
      </RaisedBadge>
      <RaisedBadge label="React" color="#0ea5e9" accent="#38bdf8" position={[-0.42, 0.72, 0.05]} rotation={[0.04, 0.1, 0]}>
        <ReactMark />
      </RaisedBadge>
      <RaisedBadge label="PostgreSQL" color="#2563eb" accent="#38bdf8" position={[0.9, -0.7, 0.25]} rotation={[-0.06, 0.2, -0.05]}>
        <DatabaseMark />
      </RaisedBadge>
      <RaisedBadge label="JavaScript" color="#eab308" accent="#facc15" position={[2.02, 0.72, 0]} rotation={[0.08, -0.28, 0.04]}>
        <JsMark />
      </RaisedBadge>
      <RaisedBadge label="REST API" color="#8b5cf6" accent="#a78bfa" position={[3.1, -0.7, 0.18]} rotation={[0.02, -0.36, 0.05]}>
        <ApiMark />
      </RaisedBadge>
      <mesh position={[0.18, -1.55, -0.35]} rotation={[-Math.PI / 2, 0, 0]}>
        <circleGeometry args={[4.35, 48]} />
        <meshBasicMaterial color="#07111b" transparent opacity={0.52} />
      </mesh>
    </group>
  )
}

export default function TechLogoScene() {
  return (
    <Canvas camera={{ position: [0, 0.1, 6.4], fov: 38 }} dpr={[1, 1.15]} gl={{ antialias: false, powerPreference: 'high-performance' }}>
      <ambientLight intensity={0.42} />
      <spotLight position={[-3.4, 4.4, 5.8]} angle={0.46} penumbra={0.7} intensity={6.5} color="#ffffff" />
      <pointLight position={[-3.5, 1.5, 3.2]} intensity={6} color="#42d9ff" />
      <pointLight position={[4.2, -1.2, 3.2]} intensity={5} color="#74f7d2" />
      <pointLight position={[0, 3.2, 2.5]} intensity={3} color="#a78bfa" />
      <Suspense fallback={null}>
        <LogoRig />
      </Suspense>
    </Canvas>
  )
}
