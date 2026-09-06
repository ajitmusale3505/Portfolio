import { Canvas, useFrame } from '@react-three/fiber'
import { Float, Stars } from '@react-three/drei'
import { Suspense, useRef } from 'react'

function DeveloperCore() {
  const group = useRef()

  useFrame((state) => {
    if (!group.current) return
    group.current.rotation.y = state.clock.elapsedTime * 0.25
    group.current.rotation.x = Math.sin(state.clock.elapsedTime * 0.35) * 0.08
  })

  return (
    <group ref={group}>
      <Float speed={2} rotationIntensity={0.45} floatIntensity={0.55}>
        <mesh position={[0, 0, 0]}>
          <icosahedronGeometry args={[1.45, 1]} />
          <meshStandardMaterial color="#74f7d2" roughness={0.35} metalness={0.18} emissive="#0f766e" emissiveIntensity={0.16} />
        </mesh>
      </Float>
      {[0, 1, 2].map((ring) => (
        <mesh key={ring} rotation={[ring * 0.75, ring * 0.45, ring * 0.35]}>
          <torusGeometry args={[2.1 + ring * 0.38, 0.012, 10, 72]} />
          <meshBasicMaterial color={ring === 0 ? '#42d9ff' : ring === 1 ? '#a78bfa' : '#84cc16'} transparent opacity={0.65} />
        </mesh>
      ))}
    </group>
  )
}

export default function HeroScene() {
  return (
    <Canvas camera={{ position: [0, 0, 6], fov: 45 }} dpr={[1, 1.2]} gl={{ antialias: false, powerPreference: 'high-performance' }}>
      <color attach="background" args={['#06070b']} />
      <ambientLight intensity={0.7} />
      <pointLight position={[5, 4, 5]} intensity={4.5} color="#60a5fa" />
      <pointLight position={[-4, -3, 4]} intensity={2.5} color="#22c55e" />
      <Suspense fallback={null}>
        <Stars radius={52} depth={28} count={280} factor={3} saturation={0} fade speed={0.18} />
        <DeveloperCore />
      </Suspense>
    </Canvas>
  )
}
