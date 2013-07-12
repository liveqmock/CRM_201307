package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import com.hundsun.jres.bizframe.core.framework.intefaces.IChainNode;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;

public abstract class NodeService implements IChainNode {

	private String nodeName="";
	private IChainNode next=null;
	
	public String getName() {
		return nodeName;
	}

	public boolean hasNext() {
		return (null!=next);
	}

	public IChainNode next() {
		return next;
	}

	public void setName(String name) {
		this.nodeName=name;
	}

	public void setNext(IChainNode next) {
		this.next=next;
	}
	
	public abstract void  process(Map<String,Object> context)throws Exception; 

}
