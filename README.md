# Java Method Sorter

This plugin provides techniques to sort methods in Java-classes, aiming to increase the readability of the source code.

## Use  
1. Via Eclipse Content Assist  
Press Ctrl + Space (Content Assist) inside the parentheses.

2. Using the Shortcut Key  
Place the cursor inside a method call or on the method name and then press Ctrl + Alt + O

## History
Fork of [Clean-Code-Method-Sorter](https://github.com/parzonka/Clean-Code-Method-Sorter) on Github.

## Build

This project uses [Tycho](https://github.com/eclipse-tycho/tycho) with [Maven](https://maven.apache.org/) to build. It requires Maven 3.9.0 or higher version.

Dev build:

```
mvn clean verify
```

Release build:

```
mvn clean org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=2.0.0 verify
```

## Install

1. Add `https://raw.githubusercontent.com/tlcsdm/eclipse-method-sorter/master/update_site/` as the upgrade location in Eclipse.
2. Download from [Jenkins](https://jenkins.tlcsdm.com/job/eclipse-plugin/job/eclipse-method-sorter)
3. <table style="border: none;">
  <tbody>
    <tr style="border:none;">
      <td style="vertical-align: middle; padding-top: 10px; border: none;">
        <a href='http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=7012266' title='Drag and drop into a running Eclipse Indigo workspace to install eclipse-method-sorter'> 
          <img src='https://marketplace.eclipse.org/modules/custom/eclipsefdn/eclipsefdn_marketplace/images/btn-install.svg'/>
        </a>
      </td>
      <td style="vertical-align: middle; text-align: left; border: none;">
        ← Drag it to your eclipse workbench to install! (I recommand Main Toolbar as Drop Target)
      </td>
    </tr>
  </tbody>
</table>
