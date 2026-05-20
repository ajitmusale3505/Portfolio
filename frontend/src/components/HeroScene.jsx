import { Canvas, useFrame } from '@react-three/fiber'
import { Float, MeshTransmissionMaterial, Stars } from '@react-three/drei'
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
          <icosahedronGeometry args={[1.45, 2]} />
          <MeshTransmissionMaterial
            thickness={0.45}
            roughness={0.22}
            transmission={0.8}
            ior={1.35}
            chromaticAberration={0.06}
            color="#74f7d2"
          />
        </mesh>
      </Float>
      {[0, 1, 2].map((ring) => (
        <mesh key={ring} rotation={[ring * 0.75, ring * 0.45, ring * 0.35]}>
          <torusGeometry args={[2.1 + ring * 0.38, 0.012, 16, 160]} />
          <meshBasicMaterial color={ring === 0 ? '#42d9ff' : ring === 1 ? '#a78bfa' : '#84cc16'} transparent opacity={0.65} />
        </mesh>
      ))}
    </group>
  )
}

export default function HeroScene() {
  return (
    <Canvas camera={{ position: [0, 0, 6], fov: 45 }} dpr={[1, 1.8]}>
      <color attach="background" args={['#06070b']} />
      <ambientLight intensity={0.7} />
      <pointLight position={[5, 4, 5]} intensity={8} color="#60a5fa" />
      <pointLight position={[-4, -3, 4]} intensity={4} color="#22c55e" />
      <Suspense fallback={null}>
        <Stars radius={60} depth={35} count={850} factor={4} saturation={0} fade speed={0.45} />
        <DeveloperCore />
      </Suspense>
    </Canvas>
  )
}
