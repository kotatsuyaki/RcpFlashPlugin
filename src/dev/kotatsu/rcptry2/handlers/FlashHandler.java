package dev.kotatsu.rcptry2.handlers;

import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class FlashHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			final Flash flash = new Flash(event);
			flash.execute();
		} catch (Exception e) {
			System.out.printf("Error caught in FlashHandler.execute: %s\n", e.toString());
		}

		return null;
	}

}

final class Flash {
	ExecutionEvent event;
	IWorkbenchWindow window;

	public Flash(ExecutionEvent event) {
		this.event = event;
		this.window = HandlerUtil.getActiveWorkbenchWindow(event);
	}

	public void execute() throws ExecutionException {
		final IFile selectedFile = extractSelecteFile();

		if (!isHexFile(selectedFile)) {
			throw new ExecutionException("Selected file is not a hex file");
		}

		final URI selectedUri = selectedFile.getLocationURI();
		openMessageDialog("Hex Flasher", String.format("Flashing hex file: %s", selectedUri.toString()));
	}

	private IFile extractSelecteFile() throws ExecutionException {
		final ISelectionService selectionService = window.getSelectionService();
		final ISelection selection = selectionService.getSelection();

		if (!(selection instanceof IStructuredSelection)) {
			throw new ExecutionException("Selection is not an IStructuredSelection");
		}
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;

		if (structuredSelection.size() != 1) {
			throw new ExecutionException(String.format("Selection has size %d != 1", structuredSelection.size()));
		}
		Object selectedItem = structuredSelection.getFirstElement();

		if (!(selectedItem instanceof IFile)) {
			throw new ExecutionException("Selection is not an IFile");
		}
		IFile selectedFile = (IFile) Platform.getAdapterManager().getAdapter(selectedItem, IFile.class);
		return selectedFile;
	}

	static private boolean isHexFile(IFile file) {
		final IPath path = file.getFullPath();
		final String extension = path.getFileExtension().toLowerCase();
		return extension.equals("hex");
	}

	private void openMessageDialog(String title, String message) {
		MessageDialog.openInformation(window.getShell(), title, message);
	}
}
