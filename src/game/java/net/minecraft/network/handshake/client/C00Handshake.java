package net.minecraft.network.handshake.client;

import java.io.IOException;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

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
public class C00Handshake implements Packet<INetHandlerHandshakeServer> {
	private int protocolVersion;
	private String ip;
	private int port;
	private EnumConnectionState requestedState;

	public C00Handshake() {
	}

	public C00Handshake(int version, String ip, int port, EnumConnectionState requestedState) {
		this.protocolVersion = version;
		this.ip = ip;
		this.port = port;
		this.requestedState = requestedState;
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.protocolVersion = parPacketBuffer.readVarIntFromBuffer();
		this.ip = parPacketBuffer.readStringFromBuffer(255);
		this.port = parPacketBuffer.readUnsignedShort();
		this.requestedState = EnumConnectionState.getById(parPacketBuffer.readVarIntFromBuffer());
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeVarIntToBuffer(this.protocolVersion);
		parPacketBuffer.writeString(this.ip);
		parPacketBuffer.writeShort(this.port);
		parPacketBuffer.writeVarIntToBuffer(this.requestedState.getId());
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerHandshakeServer inethandlerhandshakeserver) {
		inethandlerhandshakeserver.processHandshake(this);
	}

	public EnumConnectionState getRequestedState() {
		return this.requestedState;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}
}