package mapler.model;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import org.fxmisc.richtext.Caret;
import org.fxmisc.richtext.Caret.CaretVisibility;
import org.fxmisc.richtext.CaretNode;
import org.fxmisc.richtext.CaretSelectionBind;
import org.fxmisc.richtext.CharacterHit;
import org.fxmisc.richtext.MultiChangeBuilder;
import org.fxmisc.richtext.NavigationActions.SelectionPolicy;
import org.fxmisc.richtext.Selection;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.Codec;
import org.fxmisc.richtext.model.EditableStyledDocument;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.PlainTextChange;
import org.fxmisc.richtext.model.RichTextChange;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyledDocument;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.fxmisc.richtext.model.TwoDimensional.Bias;
import org.fxmisc.richtext.model.TwoDimensional.Position;
import org.fxmisc.undo.UndoManager;
import org.reactfx.EventStream;
import org.reactfx.SuspendableNo;
import org.reactfx.collection.LiveList;
import org.reactfx.util.Tuple2;
import org.reactfx.value.Val;
import org.reactfx.value.Var;
import com.sun.javafx.css.Style;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.PickRay;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.scene.input.PickResultChooser;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import com.sun.javafx.sg.prism.NGNode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.IndexRange;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Transform;
import javafx.util.Callback;

/**
 * Wrapper para a StyleClassedTextArea do console.
 * Permite mudar o comportamento da classe da area console em tempo de execução
 * @author Kerlyson
 *
 */
public class StyleClassedTextAreaWrapper {

  private StyleClassedTextArea original; // referencia original
  
  public StyleClassedTextAreaWrapper(StyleClassedTextArea original) {
    this.original = original;
  }
 
  /* DELEGAÇÕES */
  
  public void appendText(String text) {
    original.appendText(text);
  }

  public double getTotalWidthEstimate() {
    return original.getTotalWidthEstimate();
  }

  public boolean getUseInitialStyleForInsertion() {
    return original.getUseInitialStyleForInsertion();
  }

  public void append(StyledDocument<Collection<String>, String, Collection<String>> document) {
    original.append(document);
  }

  public double getTotalHeightEstimate() {
    return original.getTotalHeightEstimate();
  }

  public void undo() {
    original.undo();
  }

  public void redo() {
    original.redo();
  }

  public void append(String seg, Collection<String> style) {
    original.append(seg, style);
  }

  public double getEstimatedScrollX() {
    return original.getEstimatedScrollX();
  }

  public boolean isUndoAvailable() {
    return original.isUndoAvailable();
  }

  public void setUseInitialStyleForInsertion(boolean value) {
    original.setUseInitialStyleForInsertion(value);
  }

  public Val<Boolean> undoAvailableProperty() {
    return original.undoAvailableProperty();
  }

  public double getEstimatedScrollY() {
    return original.getEstimatedScrollY();
  }

  public void insertText(int position, String text) {
    original.insertText(position, text);
  }

  public boolean isRedoAvailable() {
    return original.isRedoAvailable();
  }

  public Val<Boolean> redoAvailableProperty() {
    return original.redoAvailableProperty();
  }

  public int getLength() {
    return original.getLength();
  }

  public void insertText(int paragraphIndex, int columnPosition, String text) {
    original.insertText(paragraphIndex, columnPosition, text);
  }

  public void scrollBy(double deltaX, double deltaY) {
    original.scrollBy(deltaX, deltaY);
  }

  public void cut() {
    original.cut();
  }

  public String getText() {
    return original.getText();
  }

  public void moveTo(int pos) {
    original.moveTo(pos);
  }

  public int hashCode() {
    return original.hashCode();
  }

  public void copy() {
    original.copy();
  }

  public void moveTo(int paragraphIndex, int columnIndex) {
    original.moveTo(paragraphIndex, columnIndex);
  }

  public void append(String text, String styleClass) {
    original.append(text, styleClass);
  }

  public void insert(int position, StyledDocument<Collection<String>, String, Collection<String>> document) {
    original.insert(position, document);
  }

  public Duration getMouseOverTextDelay() {
    return original.getMouseOverTextDelay();
  }

  public void insert(int position, String text, String styleClass) {
    original.insert(position, text, styleClass);
  }

  public void insert(int position, String seg, Collection<String> style) {
    original.insert(position, seg, style);
  }

  public void scrollToPixel(Point2D pixel) {
    original.scrollToPixel(pixel);
  }

  public int getCaretPosition() {
    return original.getCaretPosition();
  }

  public void replace(int start, int end, String text, String styleClass) {
    original.replace(start, end, text, styleClass);
  }

  public void insert(int paragraphIndex, int columnPosition, StyledDocument<Collection<String>, String, Collection<String>> document) {
    original.insert(paragraphIndex, columnPosition, document);
  }

  public void setMouseOverTextDelay(Duration delay) {
    original.setMouseOverTextDelay(delay);
  }

  public void scrollToPixel(double xPixel, double yPixel) {
    original.scrollToPixel(xPixel, yPixel);
  }

  public void setStyleClass(int from, int to, String styleClass) {
    original.setStyleClass(from, to, styleClass);
  }

  public ObservableValue<Integer> caretPositionProperty() {
    return original.caretPositionProperty();
  }

  public int getCurrentParagraph() {
    return original.getCurrentParagraph();
  }

  public void deleteText(IndexRange range) {
    original.deleteText(range);
  }

  public void paste() {
    original.paste();
  }

  public void moveTo(int position, SelectionPolicy selectionPolicy) {
    original.moveTo(position, selectionPolicy);
  }

  public ObservableValue<Integer> currentParagraphProperty() {
    return original.currentParagraphProperty();
  }

  public int getCaretColumn() {
    return original.getCaretColumn();
  }

  public void moveTo(int paragraphIndex, int columnIndex, SelectionPolicy selectionPolicy) {
    original.moveTo(paragraphIndex, columnIndex, selectionPolicy);
  }

  public void deleteText(int start, int end) {
    original.deleteText(start, end);
  }

  public ObservableValue<Integer> caretColumnProperty() {
    return original.caretColumnProperty();
  }

  public StyleSpans<Collection<String>> getStyleSpans(IndexRange range) {
    return original.getStyleSpans(range);
  }

  public Optional<Bounds> getCaretBounds() {
    return original.getCaretBounds();
  }

  public boolean equals(Object obj) {
    return original.equals(obj);
  }

  public void deleteText(int startParagraph, int startColumn, int endParagraph, int endColumn) {
    original.deleteText(startParagraph, startColumn, endParagraph, endColumn);
  }

  public ObservableValue<Optional<Bounds>> caretBoundsProperty() {
    return original.caretBoundsProperty();
  }

  public void previousChar(SelectionPolicy selectionPolicy) {
    original.previousChar(selectionPolicy);
  }

  public CaretVisibility getShowCaret() {
    return original.getShowCaret();
  }

  public void nextChar(SelectionPolicy selectionPolicy) {
    original.nextChar(selectionPolicy);
  }

  public void setShowCaret(CaretVisibility value) {
    original.setShowCaret(value);
  }

  public Var<CaretVisibility> showCaretProperty() {
    return original.showCaretProperty();
  }

  public void wordBreaksBackwards(int n, SelectionPolicy selectionPolicy) {
    original.wordBreaksBackwards(n, selectionPolicy);
  }

  public int getAnchor() {
    return original.getAnchor();
  }

  public void deletePreviousChar() {
    original.deletePreviousChar();
  }

  public ObservableValue<Integer> anchorProperty() {
    return original.anchorProperty();
  }

  public void wordBreaksForwards(int n, SelectionPolicy selectionPolicy) {
    original.wordBreaksForwards(n, selectionPolicy);
  }

  public IndexRange getSelection() {
    return original.getSelection();
  }

  public StyleSpans<Collection<String>> getStyleSpans(int paragraph, IndexRange range) {
    return original.getStyleSpans(paragraph, range);
  }

  public void deleteNextChar() {
    original.deleteNextChar();
  }

  public Consumer<Point2D> getOnNewSelectionDrag() {
    return original.getOnNewSelectionDrag();
  }

  public ObservableValue<IndexRange> selectionProperty() {
    return original.selectionProperty();
  }

  public String getSelectedText() {
    return original.getSelectedText();
  }

  public void clear() {
    original.clear();
  }

  public ObservableValue<String> selectedTextProperty() {
    return original.selectedTextProperty();
  }

  public void selectWord() {
    original.selectWord();
  }

  public void replaceText(String replacement) {
    original.replaceText(replacement);
  }

  public Optional<Bounds> getSelectionBounds() {
    return original.getSelectionBounds();
  }

  public void paragraphStart(SelectionPolicy selectionPolicy) {
    original.paragraphStart(selectionPolicy);
  }

  public void replace(StyledDocument<Collection<String>, String, Collection<String>> replacement) {
    original.replace(replacement);
  }

  public void paragraphEnd(SelectionPolicy selectionPolicy) {
    original.paragraphEnd(selectionPolicy);
  }

  public void replaceSelection(String replacement) {
    original.replaceSelection(replacement);
  }

  public ObservableValue<Optional<Bounds>> selectionBoundsProperty() {
    return original.selectionBoundsProperty();
  }

  public void setOnNewSelectionDrag(Consumer<Point2D> consumer) {
    original.setOnNewSelectionDrag(consumer);
  }

  public void start(SelectionPolicy selectionPolicy) {
    original.start(selectionPolicy);
  }

  public Paragraph<Collection<String>, String, Collection<String>> getParagraph(int index) {
    return original.getParagraph(index);
  }

  public void end(SelectionPolicy selectionPolicy) {
    original.end(selectionPolicy);
  }

  public int getParagraphLength(int index) {
    return original.getParagraphLength(index);
  }

  public void replaceSelection(StyledDocument<Collection<String>, String, Collection<String>> replacement) {
    original.replaceSelection(replacement);
  }

  public void selectParagraph() {
    original.selectParagraph();
  }

  public boolean isBeingUpdated() {
    return original.isBeingUpdated();
  }

  public void selectAll() {
    original.selectAll();
  }

  public void clearStyle(int from, int to) {
    original.clearStyle(from, to);
  }

  public void moveSelectedText(int position) {
    original.moveSelectedText(position);
  }

  public void deselect() {
    original.deselect();
  }

  public void clearStyle(int paragraph, int from, int to) {
    original.clearStyle(paragraph, from, to);
  }

  public Consumer<Point2D> getOnSelectionDrag() {
    return original.getOnSelectionDrag();
  }

  public void clearStyle(int paragraph) {
    original.clearStyle(paragraph);
  }

  public void clearParagraphStyle(int paragraph) {
    original.clearParagraphStyle(paragraph);
  }

  public void setOnSelectionDrag(Consumer<Point2D> consumer) {
    original.setOnSelectionDrag(consumer);
  }

  public String getText(int startParagraph, int startColumn, int endParagraph, int endColumn) {
    return original.getText(startParagraph, startColumn, endParagraph, endColumn);
  }

  public StyledDocument<Collection<String>, String, Collection<String>> subDocument(IndexRange range) {
    return original.subDocument(range);
  }

  public IntFunction<? extends Node> getParagraphGraphicFactory() {
    return original.getParagraphGraphicFactory();
  }

  public StyledDocument<Collection<String>, String, Collection<String>> subDocument(int startParagraph, int startColumn, int endParagraph, int endColumn) {
    return original.subDocument(startParagraph, startColumn, endParagraph, endColumn);
  }

  public void setParagraphGraphicFactory(IntFunction<? extends Node> factory) {
    original.setParagraphGraphicFactory(factory);
  }

  public IndexRange getParagraphSelection(int paragraph) {
    return original.getParagraphSelection(paragraph);
  }

  public void selectRange(int anchor, int caretPosition) {
    original.selectRange(anchor, caretPosition);
  }

  public void selectRange(int anchorParagraph, int anchorColumn, int caretPositionParagraph, int caretPositionColumn) {
    original.selectRange(anchorParagraph, anchorColumn, caretPositionParagraph, caretPositionColumn);
  }

  public void replaceText(int startParagraph, int startColumn, int endParagraph, int endColumn, String text) {
    original.replaceText(startParagraph, startColumn, endParagraph, endColumn, text);
  }

  public int firstVisibleParToAllParIndex() {
    return original.firstVisibleParToAllParIndex();
  }

  public int lastVisibleParToAllParIndex() {
    return original.lastVisibleParToAllParIndex();
  }

  public void replace(int startParagraph, int startColumn, int endParagraph, int endColumn, String seg, Collection<String> style) {
    original.replace(startParagraph, startColumn, endParagraph, endColumn, seg, style);
  }

  public void replace(int startParagraph, int startColumn, int endParagraph, int endColumn, StyledDocument<Collection<String>, String, Collection<String>> replacement) {
    original.replace(startParagraph, startColumn, endParagraph, endColumn, replacement);
  }

  public void replaceText(IndexRange range, String text) {
    original.replaceText(range, text);
  }

  public void replace(IndexRange range, String seg, Collection<String> style) {
    original.replace(range, seg, style);
  }

  public void replace(IndexRange range, StyledDocument<Collection<String>, String, Collection<String>> replacement) {
    original.replace(range, replacement);
  }

  public final BooleanProperty editableProperty() {
    return original.editableProperty();
  }

  public void setEditable(boolean value) {
    original.setEditable(value);
  }

  public boolean isEditable() {
    return original.isEditable();
  }

  public final BooleanProperty wrapTextProperty() {
    return original.wrapTextProperty();
  }

  public void setWrapText(boolean value) {
    original.setWrapText(value);
  }

  public boolean isWrapText() {
    return original.isWrapText();
  }

  public UndoManager getUndoManager() {
    return original.getUndoManager();
  }

  public void setUndoManager(UndoManager undoManager) {
    original.setUndoManager(undoManager);
  }

  public ObjectProperty<Duration> mouseOverTextDelayProperty() {
    return original.mouseOverTextDelayProperty();
  }

  public ObjectProperty<IntFunction<? extends Node>> paragraphGraphicFactoryProperty() {
    return original.paragraphGraphicFactoryProperty();
  }

  public void recreateParagraphGraphic(int parNdx) {
    original.recreateParagraphGraphic(parNdx);
  }

  public Node getParagraphGraphic(int parNdx) {
    return original.getParagraphGraphic(parNdx);
  }

  public final ObjectProperty<ContextMenu> contextMenuObjectProperty() {
    return original.contextMenuObjectProperty();
  }

  public void setContextMenu(ContextMenu menu) {
    original.setContextMenu(menu);
  }

  public ContextMenu getContextMenu() {
    return original.getContextMenu();
  }

  public final DoubleProperty contextMenuXOffsetProperty() {
    return original.contextMenuXOffsetProperty();
  }

  public void setContextMenuXOffset(double offset) {
    original.setContextMenuXOffset(offset);
  }

  public double getContextMenuXOffset() {
    return original.getContextMenuXOffset();
  }

  public final DoubleProperty contextMenuYOffsetProperty() {
    return original.contextMenuYOffsetProperty();
  }

  public void setContextMenuYOffset(double offset) {
    original.setContextMenuYOffset(offset);
  }

  public double getContextMenuYOffset() {
    return original.getContextMenuYOffset();
  }

  public BooleanProperty useInitialStyleForInsertionProperty() {
    return original.useInitialStyleForInsertionProperty();
  }

  public void setStyleCodecs(Codec<Collection<String>> paragraphStyleCodec, Codec<StyledSegment<String, Collection<String>>> styledSegCodec) {
    original.setStyleCodecs(paragraphStyleCodec, styledSegCodec);
  }

  public Optional<Tuple2<Codec<Collection<String>>, Codec<StyledSegment<String, Collection<String>>>>> getStyleCodecs() {
    return original.getStyleCodecs();
  }

  public Var<Double> estimatedScrollXProperty() {
    return original.estimatedScrollXProperty();
  }

  public Var<Double> estimatedScrollYProperty() {
    return original.estimatedScrollYProperty();
  }

  public void selectLine() {
    original.selectLine();
  }

  public final boolean addCaret(CaretNode caret) {
    return original.addCaret(caret);
  }

  public final void impl_syncPeer() {
    original.impl_syncPeer();
  }

  public final boolean removeCaret(CaretNode caret) {
    return original.removeCaret(caret);
  }

  public final boolean addSelection(Selection<Collection<String>, String, Collection<String>> selection) {
    return original.addSelection(selection);
  }

  public void hideContextMenu() {
    original.hideContextMenu();
  }

  public final boolean removeSelection(Selection<Collection<String>, String, Collection<String>> selection) {
    return original.removeSelection(selection);
  }

  public final EventHandler<MouseEvent> getOnOutsideSelectionMousePressed() {
    return original.getOnOutsideSelectionMousePressed();
  }

  public final boolean isSnapToPixel() {
    return original.isSnapToPixel();
  }

  public final void setSnapToPixel(boolean value) {
    original.setSnapToPixel(value);
  }

  public final BooleanProperty snapToPixelProperty() {
    return original.snapToPixelProperty();
  }

  public final void setOnOutsideSelectionMousePressed(EventHandler<MouseEvent> handler) {
    original.setOnOutsideSelectionMousePressed(handler);
  }

  public final ObjectProperty<EventHandler<MouseEvent>> onOutsideSelectionMousePressedProperty() {
    return original.onOutsideSelectionMousePressedProperty();
  }

  public ObservableList<Node> getChildrenUnmodifiable() {
    return original.getChildrenUnmodifiable();
  }

  public final EventHandler<MouseEvent> getOnInsideSelectionMousePressReleased() {
    return original.getOnInsideSelectionMousePressReleased();
  }

  public final void setOnInsideSelectionMousePressReleased(EventHandler<MouseEvent> handler) {
    original.setOnInsideSelectionMousePressReleased(handler);
  }

  public final ObjectProperty<EventHandler<MouseEvent>> onInsideSelectionMousePressReleasedProperty() {
    return original.onInsideSelectionMousePressReleasedProperty();
  }

  public final ObjectProperty<Consumer<Point2D>> onNewSelectionDragProperty() {
    return original.onNewSelectionDragProperty();
  }

  public final EventHandler<MouseEvent> getOnNewSelectionDragFinished() {
    return original.getOnNewSelectionDragFinished();
  }

  public final void setOnNewSelectionDragFinished(EventHandler<MouseEvent> handler) {
    original.setOnNewSelectionDragFinished(handler);
  }

  public final ObjectProperty<EventHandler<MouseEvent>> onNewSelectionDragFinishedProperty() {
    return original.onNewSelectionDragFinishedProperty();
  }

  public final ObjectProperty<Consumer<Point2D>> onSelectionDragProperty() {
    return original.onSelectionDragProperty();
  }

  public final EventHandler<MouseEvent> getOnSelectionDropped() {
    return original.getOnSelectionDropped();
  }

  public final void setOnSelectionDropped(EventHandler<MouseEvent> handler) {
    original.setOnSelectionDropped(handler);
  }

  public final ObjectProperty<EventHandler<MouseEvent>> onSelectionDroppedProperty() {
    return original.onSelectionDroppedProperty();
  }

  public final void setPadding(Insets value) {
    original.setPadding(value);
  }

  public final Insets getPadding() {
    return original.getPadding();
  }

  public final ObjectProperty<Insets> paddingProperty() {
    return original.paddingProperty();
  }

  public final BooleanProperty autoScrollOnDragDesiredProperty() {
    return original.autoScrollOnDragDesiredProperty();
  }

  public void setAutoScrollOnDragDesired(boolean val) {
    original.setAutoScrollOnDragDesired(val);
  }

  public boolean isAutoScrollOnDragDesired() {
    return original.isAutoScrollOnDragDesired();
  }

  public final ObservableValue<String> textProperty() {
    return original.textProperty();
  }

  public final StyledDocument<Collection<String>, String, Collection<String>> getDocument() {
    return original.getDocument();
  }

  public final CaretSelectionBind<Collection<String>, String, Collection<String>> getCaretSelectionBind() {
    return original.getCaretSelectionBind();
  }

  public final ObservableValue<Integer> lengthProperty() {
    return original.lengthProperty();
  }

  public LiveList<Paragraph<Collection<String>, String, Collection<String>>> getParagraphs() {
    return original.getParagraphs();
  }

  public final LiveList<Paragraph<Collection<String>, String, Collection<String>>> getVisibleParagraphs() {
    return original.getVisibleParagraphs();
  }

  public final SuspendableNo beingUpdatedProperty() {
    return original.beingUpdatedProperty();
  }

  public Node lookup(String selector) {
    return original.lookup(selector);
  }

  public Val<Double> totalWidthEstimateProperty() {
    return original.totalWidthEstimateProperty();
  }

  public Val<Double> totalHeightEstimateProperty() {
    return original.totalHeightEstimateProperty();
  }

  public final void setBackground(Background value) {
    original.setBackground(value);
  }

  public EventStream<List<RichTextChange<Collection<String>, String, Collection<String>>>> multiRichChanges() {
    return original.multiRichChanges();
  }

  public final Background getBackground() {
    return original.getBackground();
  }

  public final ObjectProperty<Background> backgroundProperty() {
    return original.backgroundProperty();
  }

  public final ObservableMap<Object, Object> getProperties() {
    return original.getProperties();
  }

  public EventStream<List<PlainTextChange>> multiPlainChanges() {
    return original.multiPlainChanges();
  }

  public final void setImpl_traversalEngine(ParentTraversalEngine value) {
    original.setImpl_traversalEngine(value);
  }

  public final EventStream<PlainTextChange> plainTextChanges() {
    return original.plainTextChanges();
  }

  public final EventStream<RichTextChange<Collection<String>, String, Collection<String>>> richChanges() {
    return original.richChanges();
  }

  public boolean hasProperties() {
    return original.hasProperties();
  }

  public final EventStream<?> viewportDirtyEvents() {
    return original.viewportDirtyEvents();
  }

  public final ParentTraversalEngine getImpl_traversalEngine() {
    return original.getImpl_traversalEngine();
  }

  public void setUserData(Object value) {
    original.setUserData(value);
  }

  public final ObjectProperty<ParentTraversalEngine> impl_traversalEngineProperty() {
    return original.impl_traversalEngineProperty();
  }

  public Object getUserData() {
    return original.getUserData();
  }

  public final EditableStyledDocument<Collection<String>, String, Collection<String>> getContent() {
    return original.getContent();
  }

  public final Collection<String> getInitialTextStyle() {
    return original.getInitialTextStyle();
  }

  public final Collection<String> getInitialParagraphStyle() {
    return original.getInitialParagraphStyle();
  }

  public final Parent getParent() {
    return original.getParent();
  }

  public final ReadOnlyObjectProperty<Parent> parentProperty() {
    return original.parentProperty();
  }

  public final BiConsumer<TextFlow, Collection<String>> getApplyParagraphStyle() {
    return original.getApplyParagraphStyle();
  }

  public final void setBorder(Border value) {
    original.setBorder(value);
  }

  public final Border getBorder() {
    return original.getBorder();
  }

  public final boolean isPreserveStyle() {
    return original.isPreserveStyle();
  }

  public final ObjectProperty<Border> borderProperty() {
    return original.borderProperty();
  }

  public final boolean isNeedsLayout() {
    return original.isNeedsLayout();
  }

  public final ReadOnlyBooleanProperty needsLayoutProperty() {
    return original.needsLayoutProperty();
  }

  public final TextOps<String, Collection<String>> getSegOps() {
    return original.getSegOps();
  }

  public final ObjectProperty<Insets> opaqueInsetsProperty() {
    return original.opaqueInsetsProperty();
  }

  public void requestLayout() {
    original.requestLayout();
  }

  public final void setOpaqueInsets(Insets value) {
    original.setOpaqueInsets(value);
  }

  public final Insets getOpaqueInsets() {
    return original.getOpaqueInsets();
  }

  public final Insets getInsets() {
    return original.getInsets();
  }

  public final ReadOnlyObjectProperty<Insets> insetsProperty() {
    return original.insetsProperty();
  }

  public double getBaselineOffset() {
    return original.getBaselineOffset();
  }

  public final Scene getScene() {
    return original.getScene();
  }

  public final ReadOnlyObjectProperty<Scene> sceneProperty() {
    return original.sceneProperty();
  }

  public final void layout() {
    original.layout();
  }

  public final double getWidth() {
    return original.getWidth();
  }

  public final ReadOnlyDoubleProperty widthProperty() {
    return original.widthProperty();
  }

  public final void setId(String value) {
    original.setId(value);
  }

  public final String getId() {
    return original.getId();
  }

  public final double getViewportHeight() {
    return original.getViewportHeight();
  }

  public final StringProperty idProperty() {
    return original.idProperty();
  }

  public final Optional<Integer> allParToVisibleParIndex(int allParIndex) {
    return original.allParToVisibleParIndex(allParIndex);
  }

  public final int visibleParToAllParIndex(int visibleParIndex) {
    return original.visibleParToAllParIndex(visibleParIndex);
  }

  public final double getHeight() {
    return original.getHeight();
  }

  public final ReadOnlyDoubleProperty heightProperty() {
    return original.heightProperty();
  }

  public final ObservableList<String> getStyleClass() {
    return original.getStyleClass();
  }

  public CharacterHit hit(double x, double y) {
    return original.hit(x, y);
  }

  public final void setStyle(String value) {
    original.setStyle(value);
  }

  public final ObservableList<String> getStylesheets() {
    return original.getStylesheets();
  }

  public List<String> impl_getAllParentStylesheets() {
    return original.impl_getAllParentStylesheets();
  }

  public final String getStyle() {
    return original.getStyle();
  }

  public final int lineIndex(int paragraphIndex, int columnPosition) {
    return original.lineIndex(paragraphIndex, columnPosition);
  }

  public int getParagraphLinesCount(int paragraphIndex) {
    return original.getParagraphLinesCount(paragraphIndex);
  }

  public Optional<Bounds> getCharacterBoundsOnScreen(int from, int to) {
    return original.getCharacterBoundsOnScreen(from, to);
  }

  public final void setMinWidth(double value) {
    original.setMinWidth(value);
  }

  public final StringProperty styleProperty() {
    return original.styleProperty();
  }

  public final double getMinWidth() {
    return original.getMinWidth();
  }

  public final DoubleProperty minWidthProperty() {
    return original.minWidthProperty();
  }

  public final void setMinHeight(double value) {
    original.setMinHeight(value);
  }

  public final double getMinHeight() {
    return original.getMinHeight();
  }

  public final DoubleProperty minHeightProperty() {
    return original.minHeightProperty();
  }

  public final void setVisible(boolean value) {
    original.setVisible(value);
  }

  public void setMinSize(double minWidth, double minHeight) {
    original.setMinSize(minWidth, minHeight);
  }

  public final boolean isVisible() {
    return original.isVisible();
  }

  public final BooleanProperty visibleProperty() {
    return original.visibleProperty();
  }

  public final String getText(int start, int end) {
    return original.getText(start, end);
  }

  public String getText(int paragraph) {
    return original.getText(paragraph);
  }

  public String getText(IndexRange range) {
    return original.getText(range);
  }

  public final void setPrefWidth(double value) {
    original.setPrefWidth(value);
  }

  public StyledDocument<Collection<String>, String, Collection<String>> subDocument(int start, int end) {
    return original.subDocument(start, end);
  }

  public final double getPrefWidth() {
    return original.getPrefWidth();
  }

  public StyledDocument<Collection<String>, String, Collection<String>> subDocument(int paragraphIndex) {
    return original.subDocument(paragraphIndex);
  }

  public final DoubleProperty prefWidthProperty() {
    return original.prefWidthProperty();
  }

  public IndexRange getParagraphSelection(Selection selection, int paragraph) {
    return original.getParagraphSelection(selection, paragraph);
  }

  public final void setCursor(Cursor value) {
    original.setCursor(value);
  }

  public final Cursor getCursor() {
    return original.getCursor();
  }

  public final ObjectProperty<Cursor> cursorProperty() {
    return original.cursorProperty();
  }

  public Collection<String> getStyleOfChar(int index) {
    return original.getStyleOfChar(index);
  }

  public final void setPrefHeight(double value) {
    original.setPrefHeight(value);
  }

  public Collection<String> getStyleAtPosition(int position) {
    return original.getStyleAtPosition(position);
  }

  public IndexRange getStyleRangeAtPosition(int position) {
    return original.getStyleRangeAtPosition(position);
  }

  public final double getPrefHeight() {
    return original.getPrefHeight();
  }

  public final DoubleProperty prefHeightProperty() {
    return original.prefHeightProperty();
  }

  public StyleSpans<Collection<String>> getStyleSpans(int from, int to) {
    return original.getStyleSpans(from, to);
  }

  public Collection<String> getStyleOfChar(int paragraph, int index) {
    return original.getStyleOfChar(paragraph, index);
  }

  public void setPrefSize(double prefWidth, double prefHeight) {
    original.setPrefSize(prefWidth, prefHeight);
  }

  public Collection<String> getStyleAtPosition(int paragraph, int position) {
    return original.getStyleAtPosition(paragraph, position);
  }

  public IndexRange getStyleRangeAtPosition(int paragraph, int position) {
    return original.getStyleRangeAtPosition(paragraph, position);
  }

  public StyleSpans<Collection<String>> getStyleSpans(int paragraph) {
    return original.getStyleSpans(paragraph);
  }

  public StyleSpans<Collection<String>> getStyleSpans(int paragraph, int from, int to) {
    return original.getStyleSpans(paragraph, from, to);
  }

  public int getAbsolutePosition(int paragraphIndex, int columnIndex) {
    return original.getAbsolutePosition(paragraphIndex, columnIndex);
  }

  public Position position(int row, int col) {
    return original.position(row, col);
  }

  public Position offsetToPosition(int charOffset, Bias bias) {
    return original.offsetToPosition(charOffset, bias);
  }

  public Bounds getVisibleParagraphBoundsOnScreen(int visibleParagraphIndex) {
    return original.getVisibleParagraphBoundsOnScreen(visibleParagraphIndex);
  }

  public final void setOpacity(double value) {
    original.setOpacity(value);
  }

  public final double getOpacity() {
    return original.getOpacity();
  }

  public Optional<Bounds> getParagraphBoundsOnScreen(int paragraphIndex) {
    return original.getParagraphBoundsOnScreen(paragraphIndex);
  }

  public final void setMaxWidth(double value) {
    original.setMaxWidth(value);
  }

  public final DoubleProperty opacityProperty() {
    return original.opacityProperty();
  }

  public final <T extends Node & Caret> Optional<Bounds> getCaretBoundsOnScreen(T caret) {
    return original.getCaretBoundsOnScreen(caret);
  }

  public final double getMaxWidth() {
    return original.getMaxWidth();
  }

  public final DoubleProperty maxWidthProperty() {
    return original.maxWidthProperty();
  }

  public void scrollXToPixel(double pixel) {
    original.scrollXToPixel(pixel);
  }

  public void scrollYToPixel(double pixel) {
    original.scrollYToPixel(pixel);
  }

  public final void setMaxHeight(double value) {
    original.setMaxHeight(value);
  }

  public void scrollXBy(double deltaX) {
    original.scrollXBy(deltaX);
  }

  public final void setBlendMode(BlendMode value) {
    original.setBlendMode(value);
  }

  public void scrollYBy(double deltaY) {
    original.scrollYBy(deltaY);
  }

  public final double getMaxHeight() {
    return original.getMaxHeight();
  }

  public final BlendMode getBlendMode() {
    return original.getBlendMode();
  }

  public void scrollBy(Point2D deltas) {
    original.scrollBy(deltas);
  }

  public final DoubleProperty maxHeightProperty() {
    return original.maxHeightProperty();
  }

  public final ObjectProperty<BlendMode> blendModeProperty() {
    return original.blendModeProperty();
  }

  public void showParagraphInViewport(int paragraphIndex) {
    original.showParagraphInViewport(paragraphIndex);
  }

  public void setMaxSize(double maxWidth, double maxHeight) {
    original.setMaxSize(maxWidth, maxHeight);
  }

  public void showParagraphAtTop(int paragraphIndex) {
    original.showParagraphAtTop(paragraphIndex);
  }

  public void showParagraphAtBottom(int paragraphIndex) {
    original.showParagraphAtBottom(paragraphIndex);
  }

  public void showParagraphRegion(int paragraphIndex, Bounds region) {
    original.showParagraphRegion(paragraphIndex, region);
  }

  public void requestFollowCaret() {
    original.requestFollowCaret();
  }

  public final void setClip(Node value) {
    original.setClip(value);
  }

  public void lineStart(SelectionPolicy policy) {
    original.lineStart(policy);
  }

  public final Node getClip() {
    return original.getClip();
  }

  public void lineEnd(SelectionPolicy policy) {
    original.lineEnd(policy);
  }

  public final ObjectProperty<Node> clipProperty() {
    return original.clipProperty();
  }

  public int getCurrentLineStartInParargraph() {
    return original.getCurrentLineStartInParargraph();
  }

  public final Shape getShape() {
    return original.getShape();
  }

  public int getCurrentLineEndInParargraph() {
    return original.getCurrentLineEndInParargraph();
  }

  public final void setShape(Shape value) {
    original.setShape(value);
  }

  public final ObjectProperty<Shape> shapeProperty() {
    return original.shapeProperty();
  }

  public void setLineHighlighterFill(Paint highlight) {
    original.setLineHighlighterFill(highlight);
  }

  public boolean isLineHighlighterOn() {
    return original.isLineHighlighterOn();
  }

  public final void setCache(boolean value) {
    original.setCache(value);
  }

  public void setLineHighlighterOn(boolean show) {
    original.setLineHighlighterOn(show);
  }

  public final boolean isCache() {
    return original.isCache();
  }

  public final BooleanProperty cacheProperty() {
    return original.cacheProperty();
  }

  public final void setScaleShape(boolean value) {
    original.setScaleShape(value);
  }

  public final boolean isScaleShape() {
    return original.isScaleShape();
  }

  public final BooleanProperty scaleShapeProperty() {
    return original.scaleShapeProperty();
  }

  public final void setCacheHint(CacheHint value) {
    original.setCacheHint(value);
  }

  public final CacheHint getCacheHint() {
    return original.getCacheHint();
  }

  public final ObjectProperty<CacheHint> cacheHintProperty() {
    return original.cacheHintProperty();
  }

  public final void setCenterShape(boolean value) {
    original.setCenterShape(value);
  }

  public final boolean isCenterShape() {
    return original.isCenterShape();
  }

  public final BooleanProperty centerShapeProperty() {
    return original.centerShapeProperty();
  }

  public void prevPage(SelectionPolicy selectionPolicy) {
    original.prevPage(selectionPolicy);
  }

  public void nextPage(SelectionPolicy selectionPolicy) {
    original.nextPage(selectionPolicy);
  }

  public final void setCacheShape(boolean value) {
    original.setCacheShape(value);
  }

  public final boolean isCacheShape() {
    return original.isCacheShape();
  }

  public final BooleanProperty cacheShapeProperty() {
    return original.cacheShapeProperty();
  }

  public final void setEffect(Effect value) {
    original.setEffect(value);
  }

  public final Effect getEffect() {
    return original.getEffect();
  }

  public final ObjectProperty<Effect> effectProperty() {
    return original.effectProperty();
  }

  public boolean isResizable() {
    return original.isResizable();
  }

  public void displaceCaret(int pos) {
    original.displaceCaret(pos);
  }

  public void setStyle(int from, int to, Collection<String> style) {
    original.setStyle(from, to, style);
  }

  public void setStyle(int paragraph, Collection<String> style) {
    original.setStyle(paragraph, style);
  }

  public void setStyle(int paragraph, int from, int to, Collection<String> style) {
    original.setStyle(paragraph, from, to, style);
  }

  public void setStyleSpans(int from, StyleSpans<? extends Collection<String>> styleSpans) {
    original.setStyleSpans(from, styleSpans);
  }

  public void resize(double width, double height) {
    original.resize(width, height);
  }

  public void setStyleSpans(int paragraph, int from, StyleSpans<? extends Collection<String>> styleSpans) {
    original.setStyleSpans(paragraph, from, styleSpans);
  }

  public final void setDepthTest(DepthTest value) {
    original.setDepthTest(value);
  }

  public final DepthTest getDepthTest() {
    return original.getDepthTest();
  }

  public void setParagraphStyle(int paragraph, Collection<String> paragraphStyle) {
    original.setParagraphStyle(paragraph, paragraphStyle);
  }

  public final ObjectProperty<DepthTest> depthTestProperty() {
    return original.depthTestProperty();
  }

  public final void setTextInsertionStyle(Collection<String> txtStyle) {
    original.setTextInsertionStyle(txtStyle);
  }

  public final Collection<String> getTextInsertionStyle() {
    return original.getTextInsertionStyle();
  }

  public final Collection<String> getTextStyleForInsertionAt(int pos) {
    return original.getTextStyleForInsertionAt(pos);
  }

  public final double minWidth(double height) {
    return original.minWidth(height);
  }

  public final void setParagraphInsertionStyle(Collection<String> paraStyle) {
    original.setParagraphInsertionStyle(paraStyle);
  }

  public final Collection<String> getParagraphInsertionStyle() {
    return original.getParagraphInsertionStyle();
  }

  public final Collection<String> getParagraphStyleForInsertionAt(int pos) {
    return original.getParagraphStyleForInsertionAt(pos);
  }

  public final double minHeight(double width) {
    return original.minHeight(width);
  }

  public void replaceText(int start, int end, String text) {
    original.replaceText(start, end, text);
  }

  public void replace(int start, int end, String seg, Collection<String> style) {
    original.replace(start, end, seg, style);
  }

  public void replace(int start, int end, StyledDocument<Collection<String>, String, Collection<String>> replacement) {
    original.replace(start, end, replacement);
  }

  public final double prefWidth(double height) {
    return original.prefWidth(height);
  }

  public MultiChangeBuilder<Collection<String>, String, Collection<String>> createMultiChange() {
    return original.createMultiChange();
  }

  public final void setDisable(boolean value) {
    original.setDisable(value);
  }

  public MultiChangeBuilder<Collection<String>, String, Collection<String>> createMultiChange(int initialNumOfChanges) {
    return original.createMultiChange(initialNumOfChanges);
  }

  public final double prefHeight(double width) {
    return original.prefHeight(width);
  }

  public final boolean isDisable() {
    return original.isDisable();
  }

  public void dispose() {
    original.dispose();
  }

  public final BooleanProperty disableProperty() {
    return original.disableProperty();
  }

  public final double maxWidth(double height) {
    return original.maxWidth(height);
  }

  public final double maxHeight(double width) {
    return original.maxHeight(width);
  }

  public final void setPickOnBounds(boolean value) {
    original.setPickOnBounds(value);
  }

  public final boolean isPickOnBounds() {
    return original.isPickOnBounds();
  }

  public final BooleanProperty pickOnBoundsProperty() {
    return original.pickOnBoundsProperty();
  }

  public final boolean isDisabled() {
    return original.isDisabled();
  }

  public final ReadOnlyBooleanProperty disabledProperty() {
    return original.disabledProperty();
  }

  public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
    return original.impl_processMXNode(alg, ctx);
  }

  public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
    return original.queryAccessibleAttribute(attribute, parameters);
  }

  public Set<Node> lookupAll(String selector) {
    return original.lookupAll(selector);
  }

  public final double snappedTopInset() {
    return original.snappedTopInset();
  }

  public final double snappedBottomInset() {
    return original.snappedBottomInset();
  }

  public void toBack() {
    original.toBack();
  }

  public final double snappedLeftInset() {
    return original.snappedLeftInset();
  }

  public void toFront() {
    original.toFront();
  }

  public final double snappedRightInset() {
    return original.snappedRightInset();
  }

  public WritableImage snapshot(SnapshotParameters params, WritableImage image) {
    return original.snapshot(params, image);
  }

  public void snapshot(Callback<SnapshotResult, Void> callback, SnapshotParameters params, WritableImage image) {
    original.snapshot(callback, params, image);
  }

  public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
    return original.getCssMetaData();
  }

  public final void setOnDragEntered(EventHandler<? super DragEvent> value) {
    original.setOnDragEntered(value);
  }

  public final EventHandler<? super DragEvent> getOnDragEntered() {
    return original.getOnDragEntered();
  }

  public final ObjectProperty<EventHandler<? super DragEvent>> onDragEnteredProperty() {
    return original.onDragEnteredProperty();
  }

  public final void setOnDragExited(EventHandler<? super DragEvent> value) {
    original.setOnDragExited(value);
  }

  public final EventHandler<? super DragEvent> getOnDragExited() {
    return original.getOnDragExited();
  }

  public final ObjectProperty<EventHandler<? super DragEvent>> onDragExitedProperty() {
    return original.onDragExitedProperty();
  }

  public final void setOnDragOver(EventHandler<? super DragEvent> value) {
    original.setOnDragOver(value);
  }

  public final EventHandler<? super DragEvent> getOnDragOver() {
    return original.getOnDragOver();
  }

  public final ObjectProperty<EventHandler<? super DragEvent>> onDragOverProperty() {
    return original.onDragOverProperty();
  }

  public final void setOnDragDropped(EventHandler<? super DragEvent> value) {
    original.setOnDragDropped(value);
  }

  public final EventHandler<? super DragEvent> getOnDragDropped() {
    return original.getOnDragDropped();
  }

  public final ObjectProperty<EventHandler<? super DragEvent>> onDragDroppedProperty() {
    return original.onDragDroppedProperty();
  }

  public final void setOnDragDone(EventHandler<? super DragEvent> value) {
    original.setOnDragDone(value);
  }

  public final EventHandler<? super DragEvent> getOnDragDone() {
    return original.getOnDragDone();
  }

  public final ObjectProperty<EventHandler<? super DragEvent>> onDragDoneProperty() {
    return original.onDragDoneProperty();
  }

  public Dragboard startDragAndDrop(TransferMode... transferModes) {
    return original.startDragAndDrop(transferModes);
  }

  public void startFullDrag() {
    original.startFullDrag();
  }

  public <P extends NGNode> P impl_getPeer() {
    return original.impl_getPeer();
  }

  public final void setManaged(boolean value) {
    original.setManaged(value);
  }

  public final boolean isManaged() {
    return original.isManaged();
  }

  public final BooleanProperty managedProperty() {
    return original.managedProperty();
  }

  public final void setLayoutX(double value) {
    original.setLayoutX(value);
  }

  public final double getLayoutX() {
    return original.getLayoutX();
  }

  public final DoubleProperty layoutXProperty() {
    return original.layoutXProperty();
  }

  public final void setLayoutY(double value) {
    original.setLayoutY(value);
  }

  public final double getLayoutY() {
    return original.getLayoutY();
  }

  public final DoubleProperty layoutYProperty() {
    return original.layoutYProperty();
  }

  public void relocate(double x, double y) {
    original.relocate(x, y);
  }

  public Orientation getContentBias() {
    return original.getContentBias();
  }

  public void impl_updatePeer() {
    original.impl_updatePeer();
  }

  public final void autosize() {
    original.autosize();
  }

  public NGNode impl_createPeer() {
    return original.impl_createPeer();
  }

  public void resizeRelocate(double x, double y, double width, double height) {
    original.resizeRelocate(x, y, width, height);
  }

  public double computeAreaInScreen() {
    return original.computeAreaInScreen();
  }

  public final Bounds getBoundsInParent() {
    return original.getBoundsInParent();
  }

  public final ReadOnlyObjectProperty<Bounds> boundsInParentProperty() {
    return original.boundsInParentProperty();
  }

  public final Bounds getBoundsInLocal() {
    return original.getBoundsInLocal();
  }

  public final ReadOnlyObjectProperty<Bounds> boundsInLocalProperty() {
    return original.boundsInLocalProperty();
  }

  public final Bounds getLayoutBounds() {
    return original.getLayoutBounds();
  }

  public final ReadOnlyObjectProperty<Bounds> layoutBoundsProperty() {
    return original.layoutBoundsProperty();
  }

  public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
    return original.impl_computeGeomBounds(bounds, tx);
  }

  public String getUserAgentStylesheet() {
    return original.getUserAgentStylesheet();
  }

  public boolean contains(double localX, double localY) {
    return original.contains(localX, localY);
  }

  public boolean contains(Point2D localPoint) {
    return original.contains(localPoint);
  }

  public boolean intersects(double localX, double localY, double localWidth, double localHeight) {
    return original.intersects(localX, localY, localWidth, localHeight);
  }

  public boolean intersects(Bounds localBounds) {
    return original.intersects(localBounds);
  }

  public Point2D screenToLocal(double screenX, double screenY) {
    return original.screenToLocal(screenX, screenY);
  }

  public Point2D screenToLocal(Point2D screenPoint) {
    return original.screenToLocal(screenPoint);
  }

  public Bounds screenToLocal(Bounds screenBounds) {
    return original.screenToLocal(screenBounds);
  }

  public Point2D sceneToLocal(double x, double y, boolean rootScene) {
    return original.sceneToLocal(x, y, rootScene);
  }

  public Point2D sceneToLocal(Point2D point, boolean rootScene) {
    return original.sceneToLocal(point, rootScene);
  }

  public Bounds sceneToLocal(Bounds bounds, boolean rootScene) {
    return original.sceneToLocal(bounds, rootScene);
  }

  public Point2D sceneToLocal(double sceneX, double sceneY) {
    return original.sceneToLocal(sceneX, sceneY);
  }

  public Point3D sceneToLocal(Point3D scenePoint) {
    return original.sceneToLocal(scenePoint);
  }

  public Point3D sceneToLocal(double sceneX, double sceneY, double sceneZ) {
    return original.sceneToLocal(sceneX, sceneY, sceneZ);
  }

  public Bounds sceneToLocal(Bounds sceneBounds) {
    return original.sceneToLocal(sceneBounds);
  }

  public Point2D localToScreen(double localX, double localY) {
    return original.localToScreen(localX, localY);
  }

  public Point2D localToScreen(Point2D localPoint) {
    return original.localToScreen(localPoint);
  }

  public Point2D localToScreen(double localX, double localY, double localZ) {
    return original.localToScreen(localX, localY, localZ);
  }

  public Point2D localToScreen(Point3D localPoint) {
    return original.localToScreen(localPoint);
  }

  public Bounds localToScreen(Bounds localBounds) {
    return original.localToScreen(localBounds);
  }

  public Point2D localToScene(double localX, double localY) {
    return original.localToScene(localX, localY);
  }

  public Point3D localToScene(Point3D localPoint) {
    return original.localToScene(localPoint);
  }

  public Point3D localToScene(double x, double y, double z) {
    return original.localToScene(x, y, z);
  }

  public Point3D localToScene(Point3D localPoint, boolean rootScene) {
    return original.localToScene(localPoint, rootScene);
  }

  public Point3D localToScene(double x, double y, double z, boolean rootScene) {
    return original.localToScene(x, y, z, rootScene);
  }

  public Point2D localToScene(Point2D localPoint, boolean rootScene) {
    return original.localToScene(localPoint, rootScene);
  }

  public Point2D localToScene(double x, double y, boolean rootScene) {
    return original.localToScene(x, y, rootScene);
  }

  public Bounds localToScene(Bounds localBounds, boolean rootScene) {
    return original.localToScene(localBounds, rootScene);
  }

  public Bounds localToScene(Bounds localBounds) {
    return original.localToScene(localBounds);
  }

  public Point2D parentToLocal(double parentX, double parentY) {
    return original.parentToLocal(parentX, parentY);
  }

  public Point3D parentToLocal(Point3D parentPoint) {
    return original.parentToLocal(parentPoint);
  }

  public Point3D parentToLocal(double parentX, double parentY, double parentZ) {
    return original.parentToLocal(parentX, parentY, parentZ);
  }

  public Bounds parentToLocal(Bounds parentBounds) {
    return original.parentToLocal(parentBounds);
  }

  public Point2D localToParent(double localX, double localY) {
    return original.localToParent(localX, localY);
  }

  public Point3D localToParent(Point3D localPoint) {
    return original.localToParent(localPoint);
  }

  public Point3D localToParent(double x, double y, double z) {
    return original.localToParent(x, y, z);
  }

  public Bounds localToParent(Bounds localBounds) {
    return original.localToParent(localBounds);
  }

  public final BaseTransform impl_getLeafTransform() {
    return original.impl_getLeafTransform();
  }

  public void impl_transformsChanged() {
    original.impl_transformsChanged();
  }

  public final double impl_getPivotX() {
    return original.impl_getPivotX();
  }

  public final double impl_getPivotY() {
    return original.impl_getPivotY();
  }

  public final double impl_getPivotZ() {
    return original.impl_getPivotZ();
  }

  public Point2D parentToLocal(Point2D pt) {
    return original.parentToLocal(pt);
  }

  public Point2D sceneToLocal(Point2D pt) {
    return original.sceneToLocal(pt);
  }

  public Point2D localToScene(Point2D pt) {
    return original.localToScene(pt);
  }

  public Point2D localToParent(Point2D pt) {
    return original.localToParent(pt);
  }

  public final void impl_pickNode(PickRay pickRay, PickResultChooser result) {
    original.impl_pickNode(pickRay, result);
  }

  public final ObservableList<Transform> getTransforms() {
    return original.getTransforms();
  }

  public final void setTranslateX(double value) {
    original.setTranslateX(value);
  }

  public final double getTranslateX() {
    return original.getTranslateX();
  }

  public final DoubleProperty translateXProperty() {
    return original.translateXProperty();
  }

  public final void setTranslateY(double value) {
    original.setTranslateY(value);
  }

  public final double getTranslateY() {
    return original.getTranslateY();
  }

  public final DoubleProperty translateYProperty() {
    return original.translateYProperty();
  }

  public final void setTranslateZ(double value) {
    original.setTranslateZ(value);
  }

  public final double getTranslateZ() {
    return original.getTranslateZ();
  }

  public final DoubleProperty translateZProperty() {
    return original.translateZProperty();
  }

  public final void setScaleX(double value) {
    original.setScaleX(value);
  }

  public final double getScaleX() {
    return original.getScaleX();
  }

  public final DoubleProperty scaleXProperty() {
    return original.scaleXProperty();
  }

  public final void setScaleY(double value) {
    original.setScaleY(value);
  }

  public final double getScaleY() {
    return original.getScaleY();
  }

  public final DoubleProperty scaleYProperty() {
    return original.scaleYProperty();
  }

  public final void setScaleZ(double value) {
    original.setScaleZ(value);
  }

  public final double getScaleZ() {
    return original.getScaleZ();
  }

  public final DoubleProperty scaleZProperty() {
    return original.scaleZProperty();
  }

  public final void setRotate(double value) {
    original.setRotate(value);
  }

  public final double getRotate() {
    return original.getRotate();
  }

  public final DoubleProperty rotateProperty() {
    return original.rotateProperty();
  }

  public final void setRotationAxis(Point3D value) {
    original.setRotationAxis(value);
  }

  public final Point3D getRotationAxis() {
    return original.getRotationAxis();
  }

  public final ObjectProperty<Point3D> rotationAxisProperty() {
    return original.rotationAxisProperty();
  }

  public final ReadOnlyObjectProperty<Transform> localToParentTransformProperty() {
    return original.localToParentTransformProperty();
  }

  public final Transform getLocalToParentTransform() {
    return original.getLocalToParentTransform();
  }

  public final ReadOnlyObjectProperty<Transform> localToSceneTransformProperty() {
    return original.localToSceneTransformProperty();
  }

  public final Transform getLocalToSceneTransform() {
    return original.getLocalToSceneTransform();
  }

  public boolean impl_hasTransforms() {
    return original.impl_hasTransforms();
  }

  public final void setNodeOrientation(NodeOrientation orientation) {
    original.setNodeOrientation(orientation);
  }

  public final NodeOrientation getNodeOrientation() {
    return original.getNodeOrientation();
  }

  public final ObjectProperty<NodeOrientation> nodeOrientationProperty() {
    return original.nodeOrientationProperty();
  }

  public final NodeOrientation getEffectiveNodeOrientation() {
    return original.getEffectiveNodeOrientation();
  }

  public final ReadOnlyObjectProperty<NodeOrientation> effectiveNodeOrientationProperty() {
    return original.effectiveNodeOrientationProperty();
  }

  public boolean usesMirroring() {
    return original.usesMirroring();
  }

  public final void setMouseTransparent(boolean value) {
    original.setMouseTransparent(value);
  }

  public final boolean isMouseTransparent() {
    return original.isMouseTransparent();
  }

  public final BooleanProperty mouseTransparentProperty() {
    return original.mouseTransparentProperty();
  }

  public final boolean isHover() {
    return original.isHover();
  }

  public final ReadOnlyBooleanProperty hoverProperty() {
    return original.hoverProperty();
  }

  public final boolean isPressed() {
    return original.isPressed();
  }

  public final ReadOnlyBooleanProperty pressedProperty() {
    return original.pressedProperty();
  }

  public final void setOnContextMenuRequested(EventHandler<? super ContextMenuEvent> value) {
    original.setOnContextMenuRequested(value);
  }

  public final EventHandler<? super ContextMenuEvent> getOnContextMenuRequested() {
    return original.getOnContextMenuRequested();
  }

  public final ObjectProperty<EventHandler<? super ContextMenuEvent>> onContextMenuRequestedProperty() {
    return original.onContextMenuRequestedProperty();
  }

  public final void setOnMouseClicked(EventHandler<? super MouseEvent> value) {
    original.setOnMouseClicked(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseClicked() {
    return original.getOnMouseClicked();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseClickedProperty() {
    return original.onMouseClickedProperty();
  }

  public final void setOnMouseDragged(EventHandler<? super MouseEvent> value) {
    original.setOnMouseDragged(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseDragged() {
    return original.getOnMouseDragged();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseDraggedProperty() {
    return original.onMouseDraggedProperty();
  }

  public final void setOnMouseEntered(EventHandler<? super MouseEvent> value) {
    original.setOnMouseEntered(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseEntered() {
    return original.getOnMouseEntered();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseEnteredProperty() {
    return original.onMouseEnteredProperty();
  }

  public final void setOnMouseExited(EventHandler<? super MouseEvent> value) {
    original.setOnMouseExited(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseExited() {
    return original.getOnMouseExited();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseExitedProperty() {
    return original.onMouseExitedProperty();
  }

  public final void setOnMouseMoved(EventHandler<? super MouseEvent> value) {
    original.setOnMouseMoved(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseMoved() {
    return original.getOnMouseMoved();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseMovedProperty() {
    return original.onMouseMovedProperty();
  }

  public final void setOnMousePressed(EventHandler<? super MouseEvent> value) {
    original.setOnMousePressed(value);
  }

  public final EventHandler<? super MouseEvent> getOnMousePressed() {
    return original.getOnMousePressed();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMousePressedProperty() {
    return original.onMousePressedProperty();
  }

  public final void setOnMouseReleased(EventHandler<? super MouseEvent> value) {
    original.setOnMouseReleased(value);
  }

  public final EventHandler<? super MouseEvent> getOnMouseReleased() {
    return original.getOnMouseReleased();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onMouseReleasedProperty() {
    return original.onMouseReleasedProperty();
  }

  public final void setOnDragDetected(EventHandler<? super MouseEvent> value) {
    original.setOnDragDetected(value);
  }

  public final EventHandler<? super MouseEvent> getOnDragDetected() {
    return original.getOnDragDetected();
  }

  public final ObjectProperty<EventHandler<? super MouseEvent>> onDragDetectedProperty() {
    return original.onDragDetectedProperty();
  }

  public final void setOnMouseDragOver(EventHandler<? super MouseDragEvent> value) {
    original.setOnMouseDragOver(value);
  }

  public final EventHandler<? super MouseDragEvent> getOnMouseDragOver() {
    return original.getOnMouseDragOver();
  }

  public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragOverProperty() {
    return original.onMouseDragOverProperty();
  }

  public final void setOnMouseDragReleased(EventHandler<? super MouseDragEvent> value) {
    original.setOnMouseDragReleased(value);
  }

  public final EventHandler<? super MouseDragEvent> getOnMouseDragReleased() {
    return original.getOnMouseDragReleased();
  }

  public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragReleasedProperty() {
    return original.onMouseDragReleasedProperty();
  }

  public final void setOnMouseDragEntered(EventHandler<? super MouseDragEvent> value) {
    original.setOnMouseDragEntered(value);
  }

  public final EventHandler<? super MouseDragEvent> getOnMouseDragEntered() {
    return original.getOnMouseDragEntered();
  }

  public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragEnteredProperty() {
    return original.onMouseDragEnteredProperty();
  }

  public final void setOnMouseDragExited(EventHandler<? super MouseDragEvent> value) {
    original.setOnMouseDragExited(value);
  }

  public final EventHandler<? super MouseDragEvent> getOnMouseDragExited() {
    return original.getOnMouseDragExited();
  }

  public final ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragExitedProperty() {
    return original.onMouseDragExitedProperty();
  }

  public final void setOnScrollStarted(EventHandler<? super ScrollEvent> value) {
    original.setOnScrollStarted(value);
  }

  public final EventHandler<? super ScrollEvent> getOnScrollStarted() {
    return original.getOnScrollStarted();
  }

  public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollStartedProperty() {
    return original.onScrollStartedProperty();
  }

  public final void setOnScroll(EventHandler<? super ScrollEvent> value) {
    original.setOnScroll(value);
  }

  public final EventHandler<? super ScrollEvent> getOnScroll() {
    return original.getOnScroll();
  }

  public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollProperty() {
    return original.onScrollProperty();
  }

  public final void setOnScrollFinished(EventHandler<? super ScrollEvent> value) {
    original.setOnScrollFinished(value);
  }

  public final EventHandler<? super ScrollEvent> getOnScrollFinished() {
    return original.getOnScrollFinished();
  }

  public final ObjectProperty<EventHandler<? super ScrollEvent>> onScrollFinishedProperty() {
    return original.onScrollFinishedProperty();
  }

  public final void setOnRotationStarted(EventHandler<? super RotateEvent> value) {
    original.setOnRotationStarted(value);
  }

  public final EventHandler<? super RotateEvent> getOnRotationStarted() {
    return original.getOnRotationStarted();
  }

  public final ObjectProperty<EventHandler<? super RotateEvent>> onRotationStartedProperty() {
    return original.onRotationStartedProperty();
  }

  public final void setOnRotate(EventHandler<? super RotateEvent> value) {
    original.setOnRotate(value);
  }

  public final EventHandler<? super RotateEvent> getOnRotate() {
    return original.getOnRotate();
  }

  public final ObjectProperty<EventHandler<? super RotateEvent>> onRotateProperty() {
    return original.onRotateProperty();
  }

  public final void setOnRotationFinished(EventHandler<? super RotateEvent> value) {
    original.setOnRotationFinished(value);
  }

  public final EventHandler<? super RotateEvent> getOnRotationFinished() {
    return original.getOnRotationFinished();
  }

  public final ObjectProperty<EventHandler<? super RotateEvent>> onRotationFinishedProperty() {
    return original.onRotationFinishedProperty();
  }

  public final void setOnZoomStarted(EventHandler<? super ZoomEvent> value) {
    original.setOnZoomStarted(value);
  }

  public final EventHandler<? super ZoomEvent> getOnZoomStarted() {
    return original.getOnZoomStarted();
  }

  public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomStartedProperty() {
    return original.onZoomStartedProperty();
  }

  public final void setOnZoom(EventHandler<? super ZoomEvent> value) {
    original.setOnZoom(value);
  }

  public final EventHandler<? super ZoomEvent> getOnZoom() {
    return original.getOnZoom();
  }

  public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomProperty() {
    return original.onZoomProperty();
  }

  public final void setOnZoomFinished(EventHandler<? super ZoomEvent> value) {
    original.setOnZoomFinished(value);
  }

  public final EventHandler<? super ZoomEvent> getOnZoomFinished() {
    return original.getOnZoomFinished();
  }

  public final ObjectProperty<EventHandler<? super ZoomEvent>> onZoomFinishedProperty() {
    return original.onZoomFinishedProperty();
  }

  public final void setOnSwipeUp(EventHandler<? super SwipeEvent> value) {
    original.setOnSwipeUp(value);
  }

  public final EventHandler<? super SwipeEvent> getOnSwipeUp() {
    return original.getOnSwipeUp();
  }

  public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeUpProperty() {
    return original.onSwipeUpProperty();
  }

  public final void setOnSwipeDown(EventHandler<? super SwipeEvent> value) {
    original.setOnSwipeDown(value);
  }

  public final EventHandler<? super SwipeEvent> getOnSwipeDown() {
    return original.getOnSwipeDown();
  }

  public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeDownProperty() {
    return original.onSwipeDownProperty();
  }

  public final void setOnSwipeLeft(EventHandler<? super SwipeEvent> value) {
    original.setOnSwipeLeft(value);
  }

  public final EventHandler<? super SwipeEvent> getOnSwipeLeft() {
    return original.getOnSwipeLeft();
  }

  public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeLeftProperty() {
    return original.onSwipeLeftProperty();
  }

  public final void setOnSwipeRight(EventHandler<? super SwipeEvent> value) {
    original.setOnSwipeRight(value);
  }

  public final EventHandler<? super SwipeEvent> getOnSwipeRight() {
    return original.getOnSwipeRight();
  }

  public final ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeRightProperty() {
    return original.onSwipeRightProperty();
  }

  public final void setOnTouchPressed(EventHandler<? super TouchEvent> value) {
    original.setOnTouchPressed(value);
  }

  public final EventHandler<? super TouchEvent> getOnTouchPressed() {
    return original.getOnTouchPressed();
  }

  public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchPressedProperty() {
    return original.onTouchPressedProperty();
  }

  public final void setOnTouchMoved(EventHandler<? super TouchEvent> value) {
    original.setOnTouchMoved(value);
  }

  public final EventHandler<? super TouchEvent> getOnTouchMoved() {
    return original.getOnTouchMoved();
  }

  public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchMovedProperty() {
    return original.onTouchMovedProperty();
  }

  public final void setOnTouchReleased(EventHandler<? super TouchEvent> value) {
    original.setOnTouchReleased(value);
  }

  public final EventHandler<? super TouchEvent> getOnTouchReleased() {
    return original.getOnTouchReleased();
  }

  public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchReleasedProperty() {
    return original.onTouchReleasedProperty();
  }

  public final void setOnTouchStationary(EventHandler<? super TouchEvent> value) {
    original.setOnTouchStationary(value);
  }

  public final EventHandler<? super TouchEvent> getOnTouchStationary() {
    return original.getOnTouchStationary();
  }

  public final ObjectProperty<EventHandler<? super TouchEvent>> onTouchStationaryProperty() {
    return original.onTouchStationaryProperty();
  }

  public final void setOnKeyPressed(EventHandler<? super KeyEvent> value) {
    original.setOnKeyPressed(value);
  }

  public final EventHandler<? super KeyEvent> getOnKeyPressed() {
    return original.getOnKeyPressed();
  }

  public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyPressedProperty() {
    return original.onKeyPressedProperty();
  }

  public final void setOnKeyReleased(EventHandler<? super KeyEvent> value) {
    original.setOnKeyReleased(value);
  }

  public final EventHandler<? super KeyEvent> getOnKeyReleased() {
    return original.getOnKeyReleased();
  }

  public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyReleasedProperty() {
    return original.onKeyReleasedProperty();
  }

  public final void setOnKeyTyped(EventHandler<? super KeyEvent> value) {
    original.setOnKeyTyped(value);
  }

  public final EventHandler<? super KeyEvent> getOnKeyTyped() {
    return original.getOnKeyTyped();
  }

  public final ObjectProperty<EventHandler<? super KeyEvent>> onKeyTypedProperty() {
    return original.onKeyTypedProperty();
  }

  public final void setOnInputMethodTextChanged(EventHandler<? super InputMethodEvent> value) {
    original.setOnInputMethodTextChanged(value);
  }

  public final EventHandler<? super InputMethodEvent> getOnInputMethodTextChanged() {
    return original.getOnInputMethodTextChanged();
  }

  public final ObjectProperty<EventHandler<? super InputMethodEvent>> onInputMethodTextChangedProperty() {
    return original.onInputMethodTextChangedProperty();
  }

  public final void setInputMethodRequests(InputMethodRequests value) {
    original.setInputMethodRequests(value);
  }

  public final InputMethodRequests getInputMethodRequests() {
    return original.getInputMethodRequests();
  }

  public final ObjectProperty<InputMethodRequests> inputMethodRequestsProperty() {
    return original.inputMethodRequestsProperty();
  }

  public final boolean isFocused() {
    return original.isFocused();
  }

  public final ReadOnlyBooleanProperty focusedProperty() {
    return original.focusedProperty();
  }

  public final void setFocusTraversable(boolean value) {
    original.setFocusTraversable(value);
  }

  public final boolean isFocusTraversable() {
    return original.isFocusTraversable();
  }

  public final BooleanProperty focusTraversableProperty() {
    return original.focusTraversableProperty();
  }

  public void requestFocus() {
    original.requestFocus();
  }

  public final boolean impl_traverse(Direction dir) {
    return original.impl_traverse(dir);
  }

  public String toString() {
    return original.toString();
  }

  public final boolean impl_isTreeVisible() {
    return original.impl_isTreeVisible();
  }

  public final void impl_setShowMnemonics(boolean value) {
    original.impl_setShowMnemonics(value);
  }

  public final boolean impl_isShowMnemonics() {
    return original.impl_isShowMnemonics();
  }

  public final BooleanProperty impl_showMnemonicsProperty() {
    return original.impl_showMnemonicsProperty();
  }

  public final void setEventDispatcher(EventDispatcher value) {
    original.setEventDispatcher(value);
  }

  public final EventDispatcher getEventDispatcher() {
    return original.getEventDispatcher();
  }

  public final ObjectProperty<EventDispatcher> eventDispatcherProperty() {
    return original.eventDispatcherProperty();
  }

  public final <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
    original.addEventHandler(eventType, eventHandler);
  }

  public final <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
    original.removeEventHandler(eventType, eventHandler);
  }

  public final <T extends Event> void addEventFilter(EventType<T> eventType, EventHandler<? super T> eventFilter) {
    original.addEventFilter(eventType, eventFilter);
  }

  public final <T extends Event> void removeEventFilter(EventType<T> eventType, EventHandler<? super T> eventFilter) {
    original.removeEventFilter(eventType, eventFilter);
  }

  public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
    return original.buildEventDispatchChain(tail);
  }

  public final void fireEvent(Event event) {
    original.fireEvent(event);
  }

  public String getTypeSelector() {
    return original.getTypeSelector();
  }

  public Styleable getStyleableParent() {
    return original.getStyleableParent();
  }

  public final ObservableMap<StyleableProperty<?>, List<Style>> impl_getStyleMap() {
    return original.impl_getStyleMap();
  }

  public final void impl_setStyleMap(ObservableMap<StyleableProperty<?>, List<Style>> styleMap) {
    original.impl_setStyleMap(styleMap);
  }

  public Map<StyleableProperty<?>, List<Style>> impl_findStyles(Map<StyleableProperty<?>, List<Style>> styleMap) {
    return original.impl_findStyles(styleMap);
  }

  public final void pseudoClassStateChanged(PseudoClass pseudoClass, boolean active) {
    original.pseudoClassStateChanged(pseudoClass, active);
  }

  public final ObservableSet<PseudoClass> getPseudoClassStates() {
    return original.getPseudoClassStates();
  }

  public final void impl_reapplyCSS() {
    original.impl_reapplyCSS();
  }

  public final void impl_processCSS(boolean reapply) {
    original.impl_processCSS(reapply);
  }

  public final void applyCss() {
    original.applyCss();
  }

  public final void setAccessibleRole(AccessibleRole value) {
    original.setAccessibleRole(value);
  }

  public final AccessibleRole getAccessibleRole() {
    return original.getAccessibleRole();
  }

  public final ObjectProperty<AccessibleRole> accessibleRoleProperty() {
    return original.accessibleRoleProperty();
  }

  public final void setAccessibleRoleDescription(String value) {
    original.setAccessibleRoleDescription(value);
  }

  public final String getAccessibleRoleDescription() {
    return original.getAccessibleRoleDescription();
  }

  public final ObjectProperty<String> accessibleRoleDescriptionProperty() {
    return original.accessibleRoleDescriptionProperty();
  }

  public final void setAccessibleText(String value) {
    original.setAccessibleText(value);
  }

  public final String getAccessibleText() {
    return original.getAccessibleText();
  }

  public final ObjectProperty<String> accessibleTextProperty() {
    return original.accessibleTextProperty();
  }

  public final void setAccessibleHelp(String value) {
    original.setAccessibleHelp(value);
  }

  public final String getAccessibleHelp() {
    return original.getAccessibleHelp();
  }

  public final ObjectProperty<String> accessibleHelpProperty() {
    return original.accessibleHelpProperty();
  }

  public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
    original.executeAccessibleAction(action, parameters);
  }

  public final void notifyAccessibleAttributeChanged(AccessibleAttribute attributes) {
    original.notifyAccessibleAttributeChanged(attributes);
  }
  
 
  
}
