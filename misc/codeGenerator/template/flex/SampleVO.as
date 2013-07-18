package com.oasis.spiderman.vo.sample
{
	import com.oasis.tmsv5.vo.BaseVO;

	[Bindable]
	[RemoteClass(alias="com.oasis.spiderman.common.vo.sample.SampleVO")]
	public class SampleVO extends BaseVO 
	{
		public var name:String;
		public var remark:String;
		
		public function SampleVO()
		{
		}
		
	}
}