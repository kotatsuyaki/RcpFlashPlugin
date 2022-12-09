package dev.kotatsu.rcptry2.startup;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class Startup implements IStartup {
	@Override
	public void earlyStartup() {
		System.out.println("Early startup");
		PlatformUI.getWorkbench().addWindowListener(new IWindowListener() {
			@Override
			public void windowActivated(IWorkbenchWindow window) {
				System.out.println("WINDOW LISTENER: Window activated");
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				System.out.println("WINDOW LISTENER: Window de-activated");
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				System.out.println("WINDOW LISTENER: Window closed");
			}

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				System.out.println("WINDOW LISTENER: Window opened");
			}
		});
	}
}
