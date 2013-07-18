package com.oasis.spiderman.so.sample
{
	import com.oasis.component.page.BasePageSO;
	
	[Bindable]
	[RemoteClass(alias="com.oasis.spiderman.common.so.sample.SampleSO")]
	public class SampleSO extends BasePageSO
	{
		public var name:String;
		
		public function SampleSO()
		{
		}

	}
}