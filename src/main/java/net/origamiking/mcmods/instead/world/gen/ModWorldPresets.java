package net.origamiking.mcmods.instead.world.gen;//package net.origamiking.mcmods.instead.world.gen;
//
//import java.util.Map;
//import java.util.Optional;
//
//import net.minecraft.registry.Registry;
//import net.minecraft.registry.RegistryKey;
//import net.minecraft.registry.entry.RegistryEntry;
//import net.minecraft.structure.StructureSet;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeKeys;
//import net.minecraft.world.biome.source.BiomeSource;
//import net.minecraft.world.biome.source.FixedBiomeSource;
//import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
//import net.minecraft.world.biome.source.TheEndBiomeSource;
//import net.minecraft.world.biome.source.MultiNoiseBiomeSource.Preset;
//import net.minecraft.world.dimension.DimensionOptions;
//import net.minecraft.world.dimension.DimensionType;
//import net.minecraft.world.dimension.DimensionTypes;
//import net.minecraft.world.gen.GeneratorOptions;
//import net.minecraft.world.gen.WorldPreset;
//import net.minecraft.world.gen.chunk.ChunkGenerator;
//import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
//import net.minecraft.world.gen.chunk.DebugChunkGenerator;
//import net.minecraft.world.gen.chunk.FlatChunkGenerator;
//import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
//import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
//
//public class ModWorldPresets {
//    public static final RegistryKey<WorldPreset> DEFAULT = of("normal");
//    public static final RegistryKey<WorldPreset> FLAT = of("flat");
//    public static final RegistryKey<WorldPreset> LARGE_BIOMES = of("large_biomes");
//    public static final RegistryKey<WorldPreset> AMPLIFIED = of("amplified");
//    public static final RegistryKey<WorldPreset> SINGLE_BIOME_SURFACE = of("single_biome_surface");
//    public static final RegistryKey<WorldPreset> DEBUG_ALL_BLOCK_STATES = of("debug_all_block_states");
//
//    public ModWorldPresets() {
//    }
//
//    public static RegistryEntry<WorldPreset> initAndGetDefault(Registry<WorldPreset> registry) {
//        return (new Registrar(registry)).initAndGetDefault();
//    }
//
//    private static RegistryKey<WorldPreset> of(String id) {
//        return RegistryKey.of(Registry.WORLD_PRESET_KEY, new Identifier(id));
//    }
//
//    public static Optional<RegistryKey<WorldPreset>> getWorldPreset(GeneratorOptions generatorOptions) {
//        ChunkGenerator chunkGenerator = generatorOptions.getChunkGenerator();
//        if (chunkGenerator instanceof FlatChunkGenerator) {
//            return Optional.of(FLAT);
//        } else {
//            return chunkGenerator instanceof DebugChunkGenerator ? Optional.of(DEBUG_ALL_BLOCK_STATES) : Optional.empty();
//        }
//    }
//
//    public static GeneratorOptions createDefaultOptions(DynamicRegistryManager dynamicRegistryManager, long seed, boolean generateStructures, boolean bonusChest) {
//        return ((WorldPreset)dynamicRegistryManager.get(Registry.WORLD_PRESET_KEY).entryOf(DEFAULT).value()).createGeneratorOptions(seed, generateStructures, bonusChest);
//    }
//
//    public static GeneratorOptions createDefaultOptions(DynamicRegistryManager dynamicRegistryManager, long seed) {
//        return createDefaultOptions(dynamicRegistryManager, seed, true, false);
//    }
//
//    public static GeneratorOptions createDefaultOptions(DynamicRegistryManager dynamicRegistryManager) {
//        return createDefaultOptions(dynamicRegistryManager, Random.create().nextLong());
//    }
//
//    public static GeneratorOptions createDemoOptions(DynamicRegistryManager dynamicRegistryManager) {
//        return createDefaultOptions(dynamicRegistryManager, (long)"North Carolina".hashCode(), true, true);
//    }
//
//    public static DimensionOptions getDefaultOverworldOptions(DynamicRegistryManager dynamicRegistryManager) {
//        return ((WorldPreset)dynamicRegistryManager.get(Registry.WORLD_PRESET_KEY).entryOf(DEFAULT).value()).getOverworldOrElseThrow();
//    }
//
//    public static class Registrar {
//        private final Registry<WorldPreset> worldPresetRegistry;
//        private final Registry<DimensionType> dimensionTypeRegistry;
//        private final Registry<Biome> biomeRegistry;
//        private final Registry<StructureSet> structureSetRegistry;
//        private final Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry;
//        private final Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseParametersRegistry;
//        private final RegistryEntry<DimensionType> overworldDimensionType;
//        private final RegistryEntry<DimensionType> theNetherDimensionType;
//        private final RegistryEntry<ChunkGeneratorSettings> netherChunkGeneratorSettings;
//        private final DimensionOptions netherDimensionOptions;
//        private final RegistryEntry<DimensionType> theEndDimensionType;
//        private final RegistryEntry<ChunkGeneratorSettings> endChunkGeneratorSettings;
//        private final DimensionOptions endDimensionOptions;
//
//        Registrar(Registry<WorldPreset> registry) {
//            this.dimensionTypeRegistry = BuiltinRegistries.DIMENSION_TYPE;
//            this.biomeRegistry = BuiltinRegistries.BIOME;
//            this.structureSetRegistry = BuiltinRegistries.STRUCTURE_SET;
//            this.chunkGeneratorSettingsRegistry = BuiltinRegistries.CHUNK_GENERATOR_SETTINGS;
//            this.noiseParametersRegistry = BuiltinRegistries.NOISE_PARAMETERS;
//            this.overworldDimensionType = this.dimensionTypeRegistry.getOrCreateEntry(DimensionTypes.OVERWORLD);
//            this.theNetherDimensionType = this.dimensionTypeRegistry.getOrCreateEntry(DimensionTypes.THE_NETHER);
//            this.netherChunkGeneratorSettings = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(ChunkGeneratorSettings.NETHER);
//            this.netherDimensionOptions = new DimensionOptions(this.theNetherDimensionType, new NoiseChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, Preset.NETHER.getBiomeSource(this.biomeRegistry), this.netherChunkGeneratorSettings));
//            this.theEndDimensionType = this.dimensionTypeRegistry.getOrCreateEntry(DimensionTypes.THE_END);
//            this.endChunkGeneratorSettings = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(ChunkGeneratorSettings.END);
//            this.endDimensionOptions = new DimensionOptions(this.theEndDimensionType, new NoiseChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, new TheEndBiomeSource(this.biomeRegistry), this.endChunkGeneratorSettings));
//            this.worldPresetRegistry = registry;
//        }
//
//        private DimensionOptions createOverworldOptions(ChunkGenerator chunkGenerator) {
//            return new DimensionOptions(this.overworldDimensionType, chunkGenerator);
//        }
//
//        private DimensionOptions createOverworldOptions(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> chunkGeneratorSettings) {
//            return this.createOverworldOptions(new NoiseChunkGenerator(this.structureSetRegistry, this.noiseParametersRegistry, biomeSource, chunkGeneratorSettings));
//        }
//
//        private WorldPreset createPreset(DimensionOptions dimensionOptions) {
//            return new WorldPreset(Map.of(DimensionOptions.OVERWORLD, dimensionOptions, DimensionOptions.NETHER, this.netherDimensionOptions, DimensionOptions.END, this.endDimensionOptions));
//        }
//
//        private RegistryEntry<WorldPreset> register(RegistryKey<WorldPreset> key, DimensionOptions dimensionOptions) {
//            return BuiltinRegistries.add(this.worldPresetRegistry, key, this.createPreset(dimensionOptions));
//        }
//
//        public RegistryEntry<WorldPreset> initAndGetDefault() {
//            MultiNoiseBiomeSource multiNoiseBiomeSource = Preset.OVERWORLD.getBiomeSource(this.biomeRegistry);
//            RegistryEntry<ChunkGeneratorSettings> registryEntry = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(ChunkGeneratorSettings.OVERWORLD);
//            this.register(net.minecraft.world.gen.WorldPresets.DEFAULT, this.createOverworldOptions(multiNoiseBiomeSource, registryEntry));
//            RegistryEntry<ChunkGeneratorSettings> registryEntry2 = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(ChunkGeneratorSettings.LARGE_BIOMES);
//            this.register(net.minecraft.world.gen.WorldPresets.LARGE_BIOMES, this.createOverworldOptions(multiNoiseBiomeSource, registryEntry2));
//            RegistryEntry<ChunkGeneratorSettings> registryEntry3 = this.chunkGeneratorSettingsRegistry.getOrCreateEntry(ChunkGeneratorSettings.AMPLIFIED);
//            this.register(net.minecraft.world.gen.WorldPresets.AMPLIFIED, this.createOverworldOptions(multiNoiseBiomeSource, registryEntry3));
//            this.register(net.minecraft.world.gen.WorldPresets.SINGLE_BIOME_SURFACE, this.createOverworldOptions(new FixedBiomeSource(this.biomeRegistry.getOrCreateEntry(BiomeKeys.PLAINS)), registryEntry));
//            this.register(net.minecraft.world.gen.WorldPresets.FLAT, this.createOverworldOptions(new FlatChunkGenerator(this.structureSetRegistry, FlatChunkGeneratorConfig.getDefaultConfig(this.biomeRegistry, this.structureSetRegistry))));
//            return this.register(net.minecraft.world.gen.WorldPresets.DEBUG_ALL_BLOCK_STATES, this.createOverworldOptions(new DebugChunkGenerator(this.structureSetRegistry, this.biomeRegistry)));
//        }
//    }
//}