package org.dts.spell.event;

import java.util.EventListener;

/**
 * This is the event based listener interface.
 *
 * @author Jason Height (jheight@chariot.net.au)
 */
public interface SpellCheckListener extends EventListener
{
  /**
   * Se llama cuando se empieza a realizar una correcci�n ortogr�fica.
   * Se puede cancelar la correci�n llamando a 
   * <code>{@link org.dts.spell.event.SpellCheckEvent#cancel() SpellCheckEvent.cancel()}</code> 
   * 
   * @param event
   */
  public void beginChecking(SpellCheckEvent event) ;
  
  /**
   * Se llama cuando se ha detectado un error en la correcci�n ortogr�fica.
   * Se puede cancelar la correci�n llamando a 
   * <code>{@link org.dts.spell.event.SpellCheckEvent#cancel() SpellCheckEvent.cancel()}</code>
   *  
   * @param event
   */
  public void spellingError(SpellCheckEvent event) ;

  /**
   * Se llama cuando se termina una correcci�n ortogr�fica.
   * Se puede cancelar la correci�n llamando a 
   * <code>{@link org.dts.spell.event.SpellCheckEvent#cancel() SpellCheckEvent.cancel()}</code> 
   * 
   * @param event
   */
  public void endChecking(SpellCheckEvent event) ;  
}
