import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { LayoutComponent } from './features/layout/layout.component';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    LayoutComponent,
    BrowserModule
],
  providers: [],
  bootstrap: [],
})
export class AppModule {}
