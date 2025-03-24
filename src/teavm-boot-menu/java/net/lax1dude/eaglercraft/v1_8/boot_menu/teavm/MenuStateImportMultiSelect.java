/*
 * Copyright (c) 2024 lax1dude. All Rights Reserved.
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

package net.lax1dude.eaglercraft.v1_8.boot_menu.teavm;

import java.util.List;

import org.teavm.jso.dom.html.HTMLElement;

import net.lax1dude.eaglercraft.v1_8.boot_menu.teavm.OfflineDownloadParser.ParsedOfflineAdapter;

public class MenuStateImportMultiSelect extends MenuState {

	protected static class BootItem implements SelectionListController.ListItem {

		protected final ParsedOfflineAdapter parsedClient;

		public BootItem(ParsedOfflineAdapter parsedClient) {
			this.parsedClient = parsedClient;
		}

		@Override
		public String getName() {
			return parsedClient.launchData.displayName;
		}

		@Override
		public boolean getAlwaysSelected() {
			return false;
		}

	}

	protected MenuState parentState;
	protected CheckboxListController<BootItem> selectionController;

	public MenuStateImportMultiSelect(MenuState parentState, List<ParsedOfflineAdapter> parsedClients) {
		this.parentState = parentState;
		List<BootItem> list = parsedClients.stream().map(BootItem::new).toList();
		selectionController = new CheckboxListController<BootItem>(BootMenuMain.bootMenuDOM.content_selection, list) {

			@Override
			protected void cancelSelected() {
				BootMenuMain.changeState(MenuStateImportMultiSelect.this.parentState);
			}

			@Override
			protected void doneSelected(List<BootItem> selectedItems) {
				if(selectedItems.isEmpty()) {
					cancelSelected();
					return;
				}
				MenuPopupStateLoading loadingScreen = new MenuPopupStateLoading("Importing clients...");
				MenuStateImportMultiSelect.this.changePopupState(loadingScreen);
				for(int i = 0, l = selectedItems.size(); i < l; ++i) {
					loadingScreen.updateMessage("Importing (" + (i + 1) + " / " + l + ")...");
					ParsedOfflineAdapter cl = selectedItems.get(i).parsedClient;
					BootMenuMain.bootMenuDataManager.installNewClientData(cl.launchData, cl.clientData, cl.blobs, true);
				}
				BootMenuMain.changeState(new MenuStateBoot(false));
			}

		};
	}

	@Override
	protected void enterState() {
		selectionController.setup();
		BootMenuDOM.show(BootMenuMain.bootMenuDOM.content_view_selection);
		BootMenuDOM.show(BootMenuMain.bootMenuDOM.footer_text_menu_select);
	}

	@Override
	protected void exitState() {
		selectionController.destroy();
		BootMenuDOM.hide(BootMenuMain.bootMenuDOM.content_view_selection);
		BootMenuDOM.hide(BootMenuMain.bootMenuDOM.footer_text_menu_select);
	}

	@Override
	protected void enterPopupBlockingState() {
		selectionController.setCursorEventsSuspended(true);
	}

	@Override
	protected void exitPopupBlockingState() {
		selectionController.setCursorEventsSuspended(false);
	}

	@Override
	protected void handleKeyDown(int keyCode) {
		if(keyCode == KeyCodes.DOM_KEY_ESCAPE) {
			BootMenuMain.changeState(MenuStateImportMultiSelect.this.parentState);
		}else {
			selectionController.handleKeyDown(keyCode);
		}
	}

	@Override
	protected void handleKeyUp(int keyCode) {
		
	}

	@Override
	protected void handleKeyRepeat(int keyCode) {
		selectionController.handleKeyRepeat(keyCode);
	}

	@Override
	protected void handleOnChanged(HTMLElement htmlElement) {
		
	}

	@Override
	protected void handleOnClick(HTMLElement htmlElement) {
		
	}

	@Override
	protected void handleOnMouseOver(HTMLElement htmlElement) {
		
	}

	@Override
	protected void update() {
		
	}

}