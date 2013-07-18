package com.oasis.spiderman.sample
{
	import com.best.oasis.flexbase.util.BeanUtils;
	import com.oasis.component.page.PageList;
	import com.oasis.security.ModelLocator;
	import com.oasis.spiderman.BaseController;
	import com.oasis.spiderman.common.SimpleServiceHelper;
	import com.oasis.spiderman.so.sample.SampleSO;
	import com.oasis.spiderman.vo.sample.SampleVO;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.remoting.RemoteObject;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class SampleController extends BaseController
	{
		
		[Inject( source="sampleServiceFacade" )]
		public var sampleService:RemoteObject;
		
		[Inject]
		public var serviceHelper : SimpleServiceHelper;
		
		public function SampleController(){
			super();
		}		

		/**
		 * get an object
		 * @param id
		 * @param result
		 * @param callback
		 * 
		 */
		public function find(vo:SampleVO,callback:Function = null):void {
			clientContext = ModelLocator.getInstance().getContext();
			serviceHelper.executeSimpleCall( sampleService.find(clientContext,vo.id),
				function(event:ResultEvent):void{
					var sample:SampleVO = event.result as SampleVO;
					BeanUtils.copyProperties(vo,sample);
					if(callback != null) {
						callback();
					}
				});
		}
		/**
		 * create an object
		 * @param vo
		 * @param callback
		 * 
		 */
		public function create(sample:SampleVO,successCallback:Function = null,failCallBack:Function = null) :void 
		{
			clientContext = ModelLocator.getInstance().getContext();
			serviceHelper.executeSimpleCall( sampleService.create(clientContext,sample),
				function(event:ResultEvent):void{
					if(successCallback != null) {
						successCallback();
					}
				},function(event:FaultEvent):void{
					if(failCallBack != null){
						failCallBack(event);
					}
				})
		}
		/**
		 * get object paged list 
		 * @param so
		 * @param result
		 * @param callback
		 * 
		 */
		public function getPageList(so:SampleSO,pageList:PageList,callback:Function = null): void 
		{
			clientContext = ModelLocator.getInstance().getContext();
			serviceHelper.executeSimpleCall( sampleService.getPagelist(clientContext,so),
				function(event:ResultEvent):void{
					var tpageList:PageList = event.result as PageList;
					BeanUtils.copyProperties(pageList,tpageList);
					if(callback != null) {
						callback();
					}
				});
		}
		/**
		 * remove objects 
		 * @param ids
		 * @param pageList
		 * @param callback
		 * 
		 */
		public function remove(ids:ArrayCollection,callback:Function=null) :void
		{
			clientContext = ModelLocator.getInstance().getContext();
			serviceHelper.executeSimpleCall( sampleService.remove(clientContext,ids),
				function(event:ResultEvent):void{
					if(callback != null) {
						callback();
					}
				});
		}
		/**
		 * update an object 
		 * @param vo
		 * @param pageList
		 * @param callback
		 * 
		 */
		public function update(vo:SampleVO,callback:Function,faultCallBack:Function): void
		{
			clientContext = ModelLocator.getInstance().getContext();
			serviceHelper.executeSimpleCall( sampleService.update(clientContext,vo),
				function(event:ResultEvent):void{
					var sample:SampleVO = event.result as SampleVO;
					BeanUtils.copyProperties(vo,sample);
					if(callback != null) {
						callback();
					}
				},function(event:FaultEvent):void{
					if(faultCallBack != null) {
						faultCallBack(event);
					}
				});
		}
		
	}
}
