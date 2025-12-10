package com.tlcsdm.eclipse.methodsorter.handler;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Small non-blocking popup that appears near the bottom-right corner. Can be
 * closed manually (Close button) or will auto-close after a timeout.
 */
public class NotificationPopup extends PopupDialog {

	private final String message;

	public NotificationPopup(Shell parent, String title, String message) {
		// style: ON_TOP so it appears above other shells; TOOL avoids taskbar icon
		super(parent, SWT.ON_TOP | SWT.TOOL | SWT.CLOSE, true, true, false, false, false, title, null);
		this.message = message;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 10;
		layout.marginHeight = 8;
		area.setLayout(layout);

		Label lbl = new Label(area, SWT.WRAP);
		lbl.setText(message);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
		gd.widthHint = 300; // reasonable width
		lbl.setLayoutData(gd);

		return area;
	}

	/**
	 * Opens the popup and positions it at bottom-right. If autoCloseMillis &gt; 0,
	 * the popup will auto-close after that many milliseconds.
	 */
	public void openAtBottomRight(int autoCloseMillis) {
		open();
		final Shell s = getShell();
		if (s == null || s.isDisposed()) {
			return;
		}
		// compute bottom-right position on primary monitor
		Display display = s.getDisplay();
		Rectangle area = display.getPrimaryMonitor().getClientArea();
		Point size = s.getSize();
		int x = area.x + area.width - size.x - 10;
		int y = area.y + area.height - size.y - 10;
		s.setLocation(x, y);

		if (autoCloseMillis > 0) {
			display.timerExec(autoCloseMillis, new Runnable() {
				@Override
				public void run() {
					if (!s.isDisposed()) {
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								if (!s.isDisposed()) {
									close();
								}
							}
						});
					}
				}
			});
		}
	}

	public static void show(final Shell parent, final String title, final String message, final int autoCloseMillis) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (parent == null || parent.isDisposed()) {
					// find a default shell
					Shell active = Display.getDefault().getActiveShell();
					NotificationPopup p = new NotificationPopup(active, title, message);
					p.openAtBottomRight(autoCloseMillis);
				} else {
					NotificationPopup p = new NotificationPopup(parent, title, message);
					p.openAtBottomRight(autoCloseMillis);
				}
			}
		});
	}
}
