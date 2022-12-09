package dev.kotatsu.rcptry2.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class FlashHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("FLASH HANDLER: Trigger class: " + event.getTrigger().getClass().getName());

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "Rcptry2", "Hello, Eclipse world");

		try {
			handleSelection();
		} catch (Exception e) {
			System.out.println("Error caught in FlashHandler::execute: " + e.toString());
		}

		return null;
	}

	void handleSelection() throws Exception {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		ISelection selection = selectionService.getSelection("org.eclipse.ui.navigator.ProjectExplorer");
		if (selection == null) {
			throw new Exception("Selection is null");
		}
		System.out.println(selection.toString());
	}
}
