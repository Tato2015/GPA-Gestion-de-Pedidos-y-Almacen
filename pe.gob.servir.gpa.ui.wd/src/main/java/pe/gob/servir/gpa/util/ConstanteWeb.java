/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li>
 * </ul>
 * Objeto: ConstantesWeb.java
 * Descripcion: Clase que declara constantes para la parte Web
 * Autor: Consultora  - P & P BMS (P & P Business Management Solutions)
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 */
package pe.gob.servir.gpa.util;

public class ConstanteWeb {

	/** Metodo Constructor */
	public ConstanteWeb() {
	}
	
	public static final String USUARIO_GPA = "GPA";
	
	public static final String REPOSITORIO_DATOS = "repositorioDatos";

	public static final String CAMPO_SELECCIONE="Seleccione";
	public static final String CAMPO_ACTIVO="Activo";
	public static final String CAMPO_INACTIVO="Inactivo";
	public static final Integer VALOR_SELECCIONE=0;
	public static final Integer VALOR_SELECCIONE_UNO=1;
	public static final Integer VALOR_REGISTRADO=1;
	public static final String CAMPO_REGISTRADO="Registrado";
	public static final Integer VALOR_ACTIVO=3;
	public static final Integer VALOR_INACTIVO=4;
	public static final String CAMPO_SI="Si";
	public static final String CAMPO_NO="No";
	public static final Integer VALOR_NO=0;
	public static final Integer VALOR_SI=1;
	public static final Integer INDICADRO_INACTIVO=0;
	public static final Integer INDICADRO_ACTIVO=1;
	
	/** RUTAS DE PROPIEDAEDS UI */
	public static final String CODIGO_SELECCIONE = "";
	public static final Integer VALOR_COMBO = 0;
	public static final String VALOR_CERO = "0";
	public static final String VALOR_UNO = "1";
	public static final Integer VALOR_DOS = 2;
	public static final Integer VALOR_TRES = 3;
	public static final Integer VALOR_CUATRO = 4;
	public static final Integer VALOR_CINCO = 5;
	public static final Integer VALOR_SEIS = 6;
	public static final String FORMATO_FECHA = "servir.ui.general.formatofecha.obligatorio";

	public static final String VALOR_SELECCIONE_PROPERTYS="servir.ui.general.select.seleccionar.item";
	public static final String VALOR_TODOS = "[Todos]";
	public static final String VALOR_NINGUNO = "[Ninguno]";
	public static final String PROPERTIES = "gpa-ui";
	
	/** RUTAS DE RECURSOS EXTERNOS */
	public static final String PROPERTIES_FS = "gpa-recursos";
	public static final String FS_CSS = "servir.ui.filesystem.recursos.css";
	public static final String FS_JS = "servir.ui.filesystem.recursos.js";
	public static final String FS_IMAGENES = "servir.ui.filesystem.recursos.imagenes";
	public static final String FS_VISTA_PREVIA = "servir.ui.filesystem.administrado.fichas";

	/** RUTA DE ARCHIVOS DPF **/
	public static final String FS_PERSONALES = "servir.ui.filesystem.recursos.pdf";
	public static final String REPORTE_PDF = "reportePDF";

	/** Reportes */
	public static final String FS_REPORT_IMAGENES = "servir.ui.filesystem.recursos.reportes.images";
	public static final String FS_REPORT_JASPER = "servir.ui.filesystem.recursos.reportes.jasper";
	public static final String FS_REPORT_JRXML = "servir.ui.filesystem.recursos.reportes.jrxml";
	public static final String FS_REPORT_STYLE = "servir.ui.filesystem.recursos.reportes.style";
			
	/**Producto**/
	public static final String TITULO_EDITAR_PRODUCTO="EDITAR PRODUCTO";
	public static final String TITULO_DETALLE_PRODUCTO="DETALLE PRODUCTO";
	public static final String TITULO_CONFIGURAR_CATALOGO="CONFIGURAR CATÁLOGO";
	/** TIPO**/
	public static final Integer TIPO_FAMILIA=1;
	public static final Integer TIPO_DOCUMENTO=30;	
	public static final Integer TIPO_GENERO=33;
	public static final Integer TIPO_CATEGORIA=25;
	public static final Integer TIPO_TIPO_PEDIDO=7;
	public static final Integer TIPO_TIPO_PEDIDO_SUMINISTRO=8;
	public static final Integer TIPO_TIPO_PEDIDO_COMESTIBLE=9;
	public static final Integer TIPO_TIPO_PEDIDO_EXTRAORDINARIO=10;
	public static final Integer TIPO_TIPO_FRECUENCIA=36;
	public static final Integer TIPO_TIPO_UNIDAD=11;
	public static final Integer TIPO_TIPO_FAMILIA=1;
	public static final Integer TIPO_TIPO_FRECUENCIA_MES=37;
	public static final Integer TIPO_TIPO_FRECUENCIA_TRIMESTRAL=38;
	public static final String  NUEVO_LISTADO="NUEVO LISTADO";
	public static final Integer TIPO_CATEGORIA_COMESTIBLE=25;
	public static final Integer TIPO_FAMILIA_COMESTIBLE=3;
	public static final String TIPO_GENERO_MASCULINO="gpa.valor.tipoGenero.masculino.integer";
	public static final String TIPO_GENERO_FEMENINO= "gpa.valor.tipoGenero.femenino.integer";
	
	
	/**estado**/
	public static final Integer ESTADO_REGISTRADO=1;
	public static final Integer ESTADO_ACTUALIZADO=2;
	public static final Integer ESTADO_ACTIVO=3;
	public static final Integer ESTADO_INACTIVO=4;
	public static final Integer ESTADO_EMITIDO=5;
	public static final String  CAMPO_EMITIDO="EMITIDO";
	/**Descripción de Opciones de documentos*/
	public static final String OPCION_TIPO_DOCUMENTOIDENTIDAD="gpa.formulario.persona.opcion.tipo.documento.ouputlabel";
	public static final String OPCION_NOMBRES="gpa.formulario.persona.opcion.nombres.ouputlabel";
	
	
	/**Persona**/
	public static final String TITULO_NUEVA_PERSONA="NUEVA PERSONA";
	public static final String TITULO_ACTUALIZAR_PERSONA="ACTUALIZAR PERSONA";
	public static final String TITULO_EDITAR_PERSONA="EDITAR PERSONA";	
	public static final String MENSAJE_EXITOSO="gpa.ui.general.mensaje.registro.exitoso";
	public static final String CORREO_ELECTRONICO_EXISTENTE="gpa.ui.general.mensaje.correo.electronico.duplicado.sistema.label";
	public static final String CORREO_ELECTRONICO_DUPLICADO="gpa.ui.general.mensaje.correo.electronico.duplicado.label";
	
	/** VALORES COMO MAXIMO/*/	
	public static final String VALOR_DOCUMENTO_DNI="gpa.ui.tipo.documento.dni.valor.maximo.integer";
	public static final String VALOR_DOCUMENTO_CARNET_EXTRANJERIA="gpa.ui.tipo.documento.carnet.extranjeria.valor.maximo.integer";
	public static final String MSJ_NUMERO_DOC_IDENTIDAD_INCOMPLETO="gpa.ui.mensaje.solicitud.numero.documentos.value.ouputlabel";
	public static final String MSJ_SOLICITUD_EXISTENTE="gpa.ui.mensaje.solicitud.existente.detalle.condicion2";
	public static final String MSJ_SOLICITUD_NO_EXISTENTE="gpa.ui.mensaje.solicitud.existente.detalle.condicion1";
	
	/**PROGRAMACION **/
	public static final Integer PARAMETROR_ANIOS=6;
	public static final Integer PARAMETROR_MESES=6;
	public static final String MENSAJE_SELECCIONAR_AREAS="Debe de seleccionar una área como mínimo.";
	public static final String MENSAJE_SELECCIONAR_PRODUCTO="Debe de agregar un producto como mínimo.";
	public static final String MENSAJE_SELECCIONAR_FRECUENCIA="Debe de ingresar año y/o mes de frecuencia";
	public static final String MENSAJE_ELIMINAR_PRODUCTO="Debe de seleccionar un producto como mínimo.";
	public static final String MENSAJE_AVISO_DETALLE="MENSAJE DEFAULTD";
	public static final String MENSAJE_SELECCIONAR_PEDIDO="Debe de seleccionar una tipo pedido.";
	
	
	/**REQUERIMIENTO USUARIO**/
	public static final String MENSAJE_REQUERIMIENTO_USUARIO_PROGRAMACION="Debe seleccionar una programación";
	public static final String MENSAJE_REQUERIMIENTO_USUARIO_PRODUCTO="Debe de seleccionar mínimo un producto";

	
	public static final String ENERO = "ENERO";
	public static final String FEBRERO = "FEBRERO ";
	public static final String MARZO = "MARZO";
	public static final String ABRIL = "ABRIL";
	public static final String MAYO = "MAYO";
	public static final String JUNIO = "JUNIO";
	public static final String JULIO = "JULIO";
	public static final String AGOSTO = "AGOSTO";
	public static final String SETIEMBRE = "SETIEMBRE";
	public static final String OCTUBRE = "OCTUBRE";
	public static final String NOVIEMBRE = "NOVIEMBRE";
	public static final String DICIEMBRE = "DICIEMBRE";
	
	
	
	public static final Integer VALOR_ENERO=1;
	public static final Integer VALOR_FEBRERO=2;
	public static final Integer VALOR_MARZO=3;
	public static final Integer VALOR_ABRIL=4;
	public static final Integer VALOR_MAYO=5;
	public static final Integer VALOR_JUNIO=6;
	public static final Integer VALOR_JULIO=7;
	public static final Integer VALOR_AGOSTO=8;
	public static final Integer VALOR_SETIEMBRE=9;
	public static final Integer VALOR_OCTUBRE=10;
	public static final Integer VALOR_NOVIEMBRE=11;
	public static final Integer VALOR_DICIEMBRE=12;
	
	public static final String DESCRIPCION_NUMERO_DOCUMENTO="gpa.ui.reporte.numero.documento.label";
	public static final String DESCRIPCION_NUMERO="gpa.ui.reporte.numero.label";
	/** descripciones de reportes excel y jasper */
	public static final String DESCRIPCION_EXCEL_INFORMACION="gpa.ui.reporte.excel.general.informacion";
	
	public static final String END_POINT="gpa.ui.conexion.url.end.point";
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

