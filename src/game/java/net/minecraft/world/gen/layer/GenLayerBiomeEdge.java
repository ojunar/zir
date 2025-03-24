package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2025 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class GenLayerBiomeEdge extends GenLayer {
	public GenLayerBiomeEdge(long parLong1, GenLayer parGenLayer) {
		super(parLong1);
		this.parent = parGenLayer;
	}

	/**+
	 * Returns a list of integer values generated by this layer.
	 * These may be interpreted as temperatures, rainfall amounts,
	 * or biomeList[] indices based on the particular GenLayer
	 * subclass.
	 */
	public int[] getInts(int i, int j, int k, int l) {
		int[] aint = this.parent.getInts(i - 1, j - 1, k + 2, l + 2);
		int[] aint1 = IntCache.getIntCache(k * l);

		for (int i1 = 0; i1 < l; ++i1) {
			for (int j1 = 0; j1 < k; ++j1) {
				this.initChunkSeed((long) (j1 + i), (long) (i1 + j));
				int k1 = aint[j1 + 1 + (i1 + 1) * (k + 2)];
				if (!this.replaceBiomeEdgeIfNecessary(aint, aint1, j1, i1, k, k1, BiomeGenBase.extremeHills.biomeID,
						BiomeGenBase.extremeHillsEdge.biomeID)
						&& !this.replaceBiomeEdge(aint, aint1, j1, i1, k, k1, BiomeGenBase.mesaPlateau_F.biomeID,
								BiomeGenBase.mesa.biomeID)
						&& !this.replaceBiomeEdge(aint, aint1, j1, i1, k, k1, BiomeGenBase.mesaPlateau.biomeID,
								BiomeGenBase.mesa.biomeID)
						&& !this.replaceBiomeEdge(aint, aint1, j1, i1, k, k1, BiomeGenBase.megaTaiga.biomeID,
								BiomeGenBase.taiga.biomeID)) {
					if (k1 == BiomeGenBase.desert.biomeID) {
						int l2 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
						int i3 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
						int j3 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
						int k3 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
						if (l2 != BiomeGenBase.icePlains.biomeID && i3 != BiomeGenBase.icePlains.biomeID
								&& j3 != BiomeGenBase.icePlains.biomeID && k3 != BiomeGenBase.icePlains.biomeID) {
							aint1[j1 + i1 * k] = k1;
						} else {
							aint1[j1 + i1 * k] = BiomeGenBase.extremeHillsPlus.biomeID;
						}
					} else if (k1 == BiomeGenBase.swampland.biomeID) {
						int l1 = aint[j1 + 1 + (i1 + 1 - 1) * (k + 2)];
						int i2 = aint[j1 + 1 + 1 + (i1 + 1) * (k + 2)];
						int j2 = aint[j1 + 1 - 1 + (i1 + 1) * (k + 2)];
						int k2 = aint[j1 + 1 + (i1 + 1 + 1) * (k + 2)];
						if (l1 != BiomeGenBase.desert.biomeID && i2 != BiomeGenBase.desert.biomeID
								&& j2 != BiomeGenBase.desert.biomeID && k2 != BiomeGenBase.desert.biomeID
								&& l1 != BiomeGenBase.coldTaiga.biomeID && i2 != BiomeGenBase.coldTaiga.biomeID
								&& j2 != BiomeGenBase.coldTaiga.biomeID && k2 != BiomeGenBase.coldTaiga.biomeID
								&& l1 != BiomeGenBase.icePlains.biomeID && i2 != BiomeGenBase.icePlains.biomeID
								&& j2 != BiomeGenBase.icePlains.biomeID && k2 != BiomeGenBase.icePlains.biomeID) {
							if (l1 != BiomeGenBase.jungle.biomeID && k2 != BiomeGenBase.jungle.biomeID
									&& i2 != BiomeGenBase.jungle.biomeID && j2 != BiomeGenBase.jungle.biomeID) {
								aint1[j1 + i1 * k] = k1;
							} else {
								aint1[j1 + i1 * k] = BiomeGenBase.jungleEdge.biomeID;
							}
						} else {
							aint1[j1 + i1 * k] = BiomeGenBase.plains.biomeID;
						}
					} else {
						aint1[j1 + i1 * k] = k1;
					}
				}
			}
		}

		return aint1;
	}

	/**+
	 * Creates a border around a biome if necessary, e.g. A
	 * transition from hot to cold climates would otherwise occur.
	 */
	private boolean replaceBiomeEdgeIfNecessary(int[] parArrayOfInt, int[] parArrayOfInt2, int parInt1, int parInt2,
			int parInt3, int parInt4, int parInt5, int parInt6) {
		if (!biomesEqualOrMesaPlateau(parInt4, parInt5)) {
			return false;
		} else {
			int i = parArrayOfInt[parInt1 + 1 + (parInt2 + 1 - 1) * (parInt3 + 2)];
			int j = parArrayOfInt[parInt1 + 1 + 1 + (parInt2 + 1) * (parInt3 + 2)];
			int k = parArrayOfInt[parInt1 + 1 - 1 + (parInt2 + 1) * (parInt3 + 2)];
			int l = parArrayOfInt[parInt1 + 1 + (parInt2 + 1 + 1) * (parInt3 + 2)];
			if (this.canBiomesBeNeighbors(i, parInt5) && this.canBiomesBeNeighbors(j, parInt5)
					&& this.canBiomesBeNeighbors(k, parInt5) && this.canBiomesBeNeighbors(l, parInt5)) {
				parArrayOfInt2[parInt1 + parInt2 * parInt3] = parInt4;
			} else {
				parArrayOfInt2[parInt1 + parInt2 * parInt3] = parInt6;
			}

			return true;
		}
	}

	/**+
	 * Creates a border around a biome.
	 */
	private boolean replaceBiomeEdge(int[] parArrayOfInt, int[] parArrayOfInt2, int parInt1, int parInt2, int parInt3,
			int parInt4, int parInt5, int parInt6) {
		if (parInt4 != parInt5) {
			return false;
		} else {
			int i = parArrayOfInt[parInt1 + 1 + (parInt2 + 1 - 1) * (parInt3 + 2)];
			int j = parArrayOfInt[parInt1 + 1 + 1 + (parInt2 + 1) * (parInt3 + 2)];
			int k = parArrayOfInt[parInt1 + 1 - 1 + (parInt2 + 1) * (parInt3 + 2)];
			int l = parArrayOfInt[parInt1 + 1 + (parInt2 + 1 + 1) * (parInt3 + 2)];
			if (biomesEqualOrMesaPlateau(i, parInt5) && biomesEqualOrMesaPlateau(j, parInt5)
					&& biomesEqualOrMesaPlateau(k, parInt5) && biomesEqualOrMesaPlateau(l, parInt5)) {
				parArrayOfInt2[parInt1 + parInt2 * parInt3] = parInt4;
			} else {
				parArrayOfInt2[parInt1 + parInt2 * parInt3] = parInt6;
			}

			return true;
		}
	}

	/**+
	 * Returns if two biomes can logically be neighbors. If one is
	 * hot and the other cold, for example, it returns false.
	 */
	private boolean canBiomesBeNeighbors(int parInt1, int parInt2) {
		if (biomesEqualOrMesaPlateau(parInt1, parInt2)) {
			return true;
		} else {
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(parInt1);
			BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(parInt2);
			if (biomegenbase != null && biomegenbase1 != null) {
				BiomeGenBase.TempCategory biomegenbase$tempcategory = biomegenbase.getTempCategory();
				BiomeGenBase.TempCategory biomegenbase$tempcategory1 = biomegenbase1.getTempCategory();
				return biomegenbase$tempcategory == biomegenbase$tempcategory1
						|| biomegenbase$tempcategory == BiomeGenBase.TempCategory.MEDIUM
						|| biomegenbase$tempcategory1 == BiomeGenBase.TempCategory.MEDIUM;
			} else {
				return false;
			}
		}
	}
}